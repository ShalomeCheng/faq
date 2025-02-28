package com.ctrip.flight.faq.model.vo;

import java.util.Optional;

/**
 * 货币值对象
 */
public record Currency(String code) {
    public Currency {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency code cannot be null or empty");
        }
        if (code.length() != 3) {
            throw new IllegalArgumentException("Currency code must be 3 characters");
        }
    }

    /**
     * 创建 Currency 实例
     * @param code 货币代码，例如 "EUR"
     * @return Optional<Currency>
     */
    public static Optional<Currency> tryCreate(String code) {
        try {
            return Optional.of(new Currency(code));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * 获取默认的 Currency
     * @return Currency 默认为 EUR
     */
    public static Currency getDefault() {
        return new Currency("EUR");
    }
} 