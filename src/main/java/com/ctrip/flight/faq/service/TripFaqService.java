package com.ctrip.flight.faq.service;

import com.ctrip.flight.faq.model.result.QuestionsResult;
import com.ctrip.flight.faq.model.response.TravixFaqResponse;
import com.ctrip.flight.faq.model.vo.*;

import java.util.concurrent.CompletableFuture;

/**
 * FAQ 服务接口
 */
public interface TripFaqService {
    /**
     * 获取 FAQ 问题列表
     *
     * @param locale 语言地区
     * @param currency 货币
     * @param affiliateCode 渠道代码
     * @param brand 品牌
     * @param session 会话标识
     * @return FAQ 问题列表
     */
    CompletableFuture<QuestionsResult<TravixFaqResponse>> getQuestions(
            Locale locale,
            Currency currency,
            AffiliateCode affiliateCode,
            Brand brand,
            SessionIdentity session
    );
} 