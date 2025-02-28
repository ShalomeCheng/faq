package com.ctrip.flight.faq.service.impl;

import com.ctrip.basebiz.bi.aithinktank.idl.AIChatService;
import com.ctrip.basebiz.bi.aithinktank.idl.BIMultiLayerFAQRequestType;
import com.ctrip.basebiz.bi.aithinktank.idl.BIMultiLayerFAQResponseType;
import com.ctrip.flight.faq.model.result.QuestionsResult;
import com.ctrip.flight.faq.model.response.TravixFaqResponse;
import com.ctrip.flight.faq.model.response.TravixFaqResponse.FaqCategory;
import com.ctrip.flight.faq.model.response.TravixFaqResponse.FaqQuestion;
import com.ctrip.flight.faq.model.vo.*;
import com.ctrip.flight.faq.qconfig.entity.TripFaqSettings;
import com.ctrip.flight.faq.service.TripFaqService;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.framework.spring.boot.credis.CRedisClient;
import com.ctrip.framework.spring.boot.soa.SOAClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import credis.java.client.CacheProvider;
import org.springframework.stereotype.Service;
import qunar.tc.qconfig.client.spring.QConfig;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * FAQ 服务实现
 */
@Service
public class TripFaqServiceImpl implements TripFaqService {

    private static final String CACHE_KEY_PREFIX = "TRIP_FAQ_CACHE:";
    private static final Duration DEFAULT_CACHE_DURATION = Duration.ofHours(1);

    @QConfig("trip-faq-settings.json")
    private TripFaqSettings tripFaqSettings;

    @SOAClient
    private AIChatService aiChatService;
    
    @CRedisClient("RedisTest5group")
    private CacheProvider cacheProvider;

    private final ObjectMapper objectMapper;

    public TripFaqServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public CompletableFuture<QuestionsResult<TravixFaqResponse>> getQuestions(
            Locale locale,
            Currency currency,
            AffiliateCode affiliateCode,
            Brand brand,
            SessionIdentity session) {

        if (affiliateCode == null) {
            return CompletableFuture.completedFuture(
                    QuestionsResult.failure("affiliateCode is a mandatory parameter, but was not provided"));
        }

        // 测试环境特殊处理
        final AffiliateCode finalAffiliateCode = !tripFaqSettings.getTestAffiliateOverride().isEmpty() && isTestEnvironment() 
            ? new AffiliateCode(tripFaqSettings.getTestAffiliateOverride())
            : affiliateCode;

        // 构建缓存键
        String cacheKey = buildCacheKey(brand, finalAffiliateCode, locale, currency);

        // 从缓存获取或调用服务
        return CompletableFuture.supplyAsync(() -> {
            try {
                String cachedValue = cacheProvider.get(cacheKey);
                if (cachedValue != null) {
                    // 反序列化缓存数据为 TravixFaqResponse
                    TravixFaqResponse cachedResponse = objectMapper.readValue(cachedValue, TravixFaqResponse.class);
                    return QuestionsResult.success(cachedResponse);
                }

                // 使用配置中的 Groups 查找
                String groupCode = tripFaqSettings.isGroupTagTestStrategy() 
                    ? finalAffiliateCode.code()
                    : "Travix";
                    
                var groupSetting = tripFaqSettings.getGroups().stream()
                    .filter(g -> g.getGroupCode().equalsIgnoreCase(groupCode))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unable to match groupCode: " + groupCode));

                // 构建请求
                BIMultiLayerFAQRequestType request = new BIMultiLayerFAQRequestType();
                request.setSessionId(session.value());
                request.setAppId(tripFaqSettings.getAppId());
                request.setGroupCode(groupSetting.getGroupCode());
                request.setTag(groupSetting.getTag());
                request.setCorp("ctrip");
                request.setCasevalue(isTestEnvironment() ? 1L : -1L);
                request.setExtData(Map.of(
                    "AffiliateCode", finalAffiliateCode.code(),
                    "Brand", brand.code(),
                    "Currency", currency.code(),
                    "locale", locale.value()
                ));

                // 调用 SOA 服务
                BIMultiLayerFAQResponseType response = aiChatService.getBIMultiLayerFAQ(request);
                
                // 将 BIMultiLayerFAQResponseType 转换为 TravixFaqResponse
                TravixFaqResponse travixResponse = convertToTravixResponse(response);

                // 缓存响应数据
                String serializedResponse = objectMapper.writeValueAsString(travixResponse);
                cacheProvider.setex(cacheKey, tripFaqSettings.getCacheDurationInSeconds(), serializedResponse);

                return QuestionsResult.success(travixResponse);
            } catch (Exception e) {
                return QuestionsResult.failure("Failed to fetch FAQ data: " + e.getMessage());
            }
        });
    }

    /**
     * 将 BIMultiLayerFAQResponseType 转换为 TravixFaqResponse
     */
    private TravixFaqResponse convertToTravixResponse(BIMultiLayerFAQResponseType response) {
        if (response == null || response.getQuestions() == null) {
            return new TravixFaqResponse(Collections.emptyList());
        }

        List<FaqCategory> categories = new ArrayList<>();
        
        // 收集所有叶子节点问题
        Map<String, List<com.ctrip.basebiz.bi.aithinktank.idl.MultiLayerFAQ>> questionsByCategory = 
            response.getQuestions().stream()
                .filter(q -> q.isIsleaf()) // 只处理叶子节点
                .collect(Collectors.groupingBy(
                    q -> q.getCategory() != null ? q.getCategory() : "uncategorized"
                ));

        // 处理每个分类
        questionsByCategory.forEach((categoryName, questions) -> {
            List<FaqQuestion> categoryQuestions = questions.stream()
                .map(q -> new FaqQuestion(
                    q.getQuestionId(),
                    q.getQuestionStr(),
                    q.getOther(), // 答案内容
                    extractTags(q), // 从问题中提取标签
                    extractUpdateTime(q) // 从问题中提取更新时间
                ))
                .collect(Collectors.toList());

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

            FaqCategory category = new FaqCategory(
                categoryName,
                categoryName, // 标题
                categoryIcon != null ? categoryIcon : "", // 使用图标作为描述
                categoryQuestions
            );

            categories.add(category);
        });

        return new TravixFaqResponse(categories);
    }

    /**
     * 从问题中提取标签
     */
    private List<String> extractTags(com.ctrip.basebiz.bi.aithinktank.idl.MultiLayerFAQ question) {
        List<String> tags = new ArrayList<>();
        if (question.getLabel() != null && !question.getLabel().isEmpty()) {
            tags.add(question.getLabel());
        }
        return tags;
    }

    /**
     * 从问题中提取更新时间
     */
    private long extractUpdateTime(com.ctrip.basebiz.bi.aithinktank.idl.MultiLayerFAQ question) {
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
        return "FWS".equals(Foundation.server().getEnvType());
    }

    private String buildCacheKey(Brand brand, AffiliateCode affiliateCode, Locale locale, Currency currency) {
        return CACHE_KEY_PREFIX + String.join("_",
                brand.code(),
                affiliateCode.code(),
                locale.value(),
                currency.code());
    }
} 