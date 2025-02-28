package com.ctrip.flight.faq.model.vo;

import java.util.Optional;

/**
 * 渠道代码值对象
 */
public record AffiliateCode(String code) {
    public AffiliateCode {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Affiliate code cannot be null or empty");
        }
    }

    /**
     * 创建 AffiliateCode 实例
     * @param code 渠道代码
     * @return Optional<AffiliateCode>
     */
    public static Optional<AffiliateCode> tryCreate(String code) {
        try {
            return Optional.of(new AffiliateCode(code));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
} 