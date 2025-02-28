package com.ctrip.flight.faq.model.vo;

import java.util.Optional;

/**
 * 会话标识值对象
 */
public record SessionIdentity(String value) {
    public SessionIdentity {
        if (value == null) {
            throw new IllegalArgumentException("Session identity value cannot be null");
        }
    }

    /**
     * 创建 SessionIdentity 实例
     * @param value 会话标识值
     * @return Optional<SessionIdentity>
     */
    public static Optional<SessionIdentity> tryCreate(String value) {
        try {
            return Optional.of(new SessionIdentity(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * 获取匿名会话标识
     * @return SessionIdentity
     */
    public static SessionIdentity anonymous() {
        return new SessionIdentity("anonymous");
    }
} 