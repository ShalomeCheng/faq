package com.ctrip.flight.faq.model.vo;

import java.util.Optional;

/**
 * 语言地区值对象
 */
public record Locale(String value) {
    public Locale {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Locale value cannot be null or empty");
        }
    }

    /**
     * 创建 Locale 实例
     * @param value 语言地区代码，例如 "en-US"
     * @return Optional<Locale>
     */
    public static Optional<Locale> tryCreate(String value) {
        try {
            return Optional.of(new Locale(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * 获取默认的 Locale
     * @return Locale 默认为 en-US
     */
    public static Locale getDefault() {
        return new Locale("en-US");
    }
} 