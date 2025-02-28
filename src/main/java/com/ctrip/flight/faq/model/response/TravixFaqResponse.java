package com.ctrip.flight.faq.model.response;

import java.util.List;

/**
 * FAQ 响应数据模型
 */
public record TravixFaqResponse(
    List<FaqCategory> categories
) {
    /**
     * FAQ 分类
     */
    public record FaqCategory(
        String id,
        String name,
        String description,
        List<FaqQuestion> questions
    ) {}

    /**
     * FAQ 问题
     */
    public record FaqQuestion(
        String id,
        String title,
        String content,
        List<String> tags,
        long updatedAt
    ) {}
} 