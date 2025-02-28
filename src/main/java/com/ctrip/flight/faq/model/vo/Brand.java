package com.ctrip.flight.faq.model.vo;

import java.util.Optional;

/**
 * 品牌值对象
 */
public record Brand(String code) {
    public Brand {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand code cannot be null or empty");
        }
    }

    /**
     * 创建 Brand 实例
     * @param code 品牌代码
     * @return Optional<Brand>
     */
    public static Optional<Brand> tryCreate(String code) {
        try {
            return Optional.of(new Brand(code));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * 获取默认的 Brand
     * @return Brand 默认为 unknown_brand
     */
    public static Brand getDefault() {
        return new Brand("unknown_brand");
    }
} 