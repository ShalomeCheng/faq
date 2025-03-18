package com.ctrip.flight.faq.service;

import com.ctrip.basebiz.bi.aithinktank.idl.AIChatService;
import com.ctrip.basebiz.bi.aithinktank.idl.BIMultiLayerFAQRequestType;
import com.ctrip.basebiz.bi.aithinktank.idl.BIMultiLayerFAQResponseType;
import com.ctrip.basebiz.bi.aithinktank.idl.MultiLayerFAQ;
import com.ctrip.flight.faq.qconfig.TripFaqSettings;
import com.ctrip.flight.faq.soa.FaqCategoryType;
import com.ctrip.flight.faq.soa.FaqQuestionType;
import com.ctrip.flight.faq.soa.TripFaqResponse;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.framework.spring.boot.credis.CRedisClient;
import com.ctrip.framework.spring.boot.soa.SOAClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import credis.java.client.CacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import qunar.tc.qconfig.client.spring.QConfig;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FAQ业务服务，处理核心业务逻辑
 */
@Service
public class TripFaqBusinessService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TripFaqBusinessService.class);
    private static final String CACHE_KEY_PREFIX = "TRIP_FAQ_CACHE:";

    @QConfig("trip-faq-settings.json")
    private TripFaqSettings tripFaqSettings;

    @SOAClient
    private AIChatService aiChatService;
    
    @CRedisClient("RedisTest5group")
    private CacheProvider cacheProvider;

    private final ObjectMapper objectMapper;

    public TripFaqBusinessService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 获取FAQ数据
     * 
     * @param affiliateCode 渠道代码（必填）
     * @param brand 品牌代码，默认为 "unknown_brand"
     * @param locale 语言地区，默认为 "en-US"
     * @param currency 货币代码，默认为 "EUR"
     * @param sessionId 会话ID，默认为 "anonymous"
     * @return FAQ响应数据
     */
    public TripFaqResponse getFaqData(String affiliateCode, String brand, String locale, String currency, String sessionId) throws Exception {
        if (affiliateCode == null || affiliateCode.isEmpty()) {
            throw new IllegalArgumentException("affiliateCode is a mandatory parameter");
        }
        
        // 测试环境特殊处理
        final String finalAffiliateCode = !tripFaqSettings.getTestAffiliateOverride().isEmpty() && isTestEnvironment() 
            ? tripFaqSettings.getTestAffiliateOverride()
            : affiliateCode;

        // 设置默认值
        locale = locale != null ? locale : "en-US";
        currency = currency != null ? currency : "EUR";
        brand = brand != null ? brand : "unknown_brand";
        sessionId = sessionId != null ? sessionId : "anonymous";

        // 构建缓存键
        String cacheKey = buildCacheKey(brand, finalAffiliateCode, locale, currency);

        // 尝试从缓存获取数据
        String cachedValue = cacheProvider.get(cacheKey);
        if (cachedValue != null) {
            LOGGER.info("Cache hit for key: {}", cacheKey);
            return objectMapper.readValue(cachedValue, TripFaqResponse.class);
        }
        
        LOGGER.info("Cache miss for key: {}, fetching data from AI service", cacheKey);

        // 使用配置中的 Groups 查找
        String groupCode = tripFaqSettings.isGroupTagTestStrategy() 
            ? finalAffiliateCode
            : "Travix";
            
        var groupSetting = tripFaqSettings.getGroups().stream()
            .filter(g -> g.getGroupCode().equalsIgnoreCase(groupCode))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unable to match groupCode: " + groupCode));

        // 构建请求
        BIMultiLayerFAQRequestType tripRequest = new BIMultiLayerFAQRequestType();
        if (sessionId != null) {
            tripRequest.setSessionId(sessionId);
        }
        tripRequest.setAppId(tripFaqSettings.getAppId());
        tripRequest.setGroupCode(groupSetting.getGroupCode());
        tripRequest.setTag(groupSetting.getTag());
        tripRequest.setCorp("ctrip");
        tripRequest.setCasevalue(isTestEnvironment() ? 1L : -1L);
        tripRequest.setExtData(Map.of(
            "AffiliateCode", finalAffiliateCode,
            "Brand", brand,
            "Currency", currency,
            "locale", locale
        ));

        // 调用 AI 服务
        BIMultiLayerFAQResponseType response = aiChatService.getBIMultiLayerFAQ(tripRequest);
        
        // 将 BIMultiLayerFAQResponseType 转换为 TripFaqResponse
        TripFaqResponse tripResponse = convertToTripFaqResponse(response);

        // 缓存响应数据
        String serializedResponse = objectMapper.writeValueAsString(tripResponse);
        cacheProvider.setex(cacheKey, tripFaqSettings.getCacheDurationInSeconds(), serializedResponse);
        
        LOGGER.info("Data successfully fetched and cached for key: {}", cacheKey);
        
        return tripResponse;
    }
    
    /**
     * 将 BIMultiLayerFAQResponseType 转换为 TripFaqResponse
     */
    private TripFaqResponse convertToTripFaqResponse(BIMultiLayerFAQResponseType response) {
        List<FaqCategoryType> categories = new ArrayList<>();
        
        if (response == null || response.getQuestions() == null) {
            return new TripFaqResponse(categories);
        }
        
        // 收集所有叶子节点问题
        Map<String, List<MultiLayerFAQ>> questionsByCategory = 
            response.getQuestions().stream()
                .filter(q -> q.isIsleaf()) // 只处理叶子节点
                .collect(Collectors.groupingBy(
                    q -> q.getCategory() != null ? q.getCategory() : "uncategorized"
                ));

        // 处理每个分类
        questionsByCategory.forEach((categoryName, questions) -> {
            List<FaqQuestionType> categoryQuestions = new ArrayList<>();
            
            // 处理问题
            for (MultiLayerFAQ q : questions) {
                FaqQuestionType question = new FaqQuestionType(
                    q.getQuestionId(),
                    q.getQuestionStr(),
                    q.getOther(),
                    extractTags(q),
                    extractUpdateTime(q)
                );
                categoryQuestions.add(question);
            }

            // 查找分类的图标（如果有）
            String categoryIcon = null;
            if (response.getIconQuestions() != null) {
                var iconQuestion = response.getIconQuestions().stream()
                    .filter(iq -> categoryName.equals(iq.getCategory()))
                    .findFirst();
                if (iconQuestion.isPresent()) {
                    categoryIcon = iconQuestion.get().getIcon();
                }
            }

            FaqCategoryType category = new FaqCategoryType(
                categoryName,
                categoryName,
                categoryIcon != null ? categoryIcon : "",
                categoryQuestions
            );

            categories.add(category);
        });

        return new TripFaqResponse(categories);
    }

    /**
     * 从问题中提取标签
     */
    private List<String> extractTags(MultiLayerFAQ question) {
        List<String> tags = new ArrayList<>();
        if (question.getLabel() != null && !question.getLabel().isEmpty()) {
            tags.add(question.getLabel());
        }
        return tags;
    }

    /**
     * 从问题中提取更新时间
     */
    private long extractUpdateTime(MultiLayerFAQ question) {
        try {
            // 使用 score 字段作为时间戳（如果可用），否则使用当前时间
            return question.getScore() != null ? 
                   question.getScore().longValue() : 
                   Instant.now().getEpochSecond();
        } catch (Exception e) {
            return Instant.now().getEpochSecond();
        }
    }

    private boolean isTestEnvironment() {
        return Foundation.server().getEnv().isFWS();
    }

    private String buildCacheKey(String brand, String affiliateCode, String locale, String currency) {
        return CACHE_KEY_PREFIX + String.join("_",
                brand,
                affiliateCode,
                locale,
                currency);
    }
} 