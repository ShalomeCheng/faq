package com.ctrip.soa.flight.faq.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trip FAQ 查询请求
 */
public class TripFaqRequest {
    @JsonProperty("AppId")
    private String appId;

    @JsonProperty("GroupCode")
    private String groupCode;

    @JsonProperty("Tag")
    private String tag;

    @JsonProperty("Brand")
    private String brand;

    @JsonProperty("AffiliateCode")
    private String affiliateCode;

    @JsonProperty("Locale")
    private String locale;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("SessionId")
    private String sessionId;

    // Getters and Setters
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAffiliateCode() {
        return affiliateCode;
    }

    public void setAffiliateCode(String affiliateCode) {
        this.affiliateCode = affiliateCode;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
} 