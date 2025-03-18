package com.ctrip.flight.faq.soa;

import com.ctriposs.baiji.exception.BaijiRuntimeException;
import com.ctriposs.baiji.schema.*;
import com.ctriposs.baiji.specific.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;

/**
 * FAQ 查询请求类型
 */
@SuppressWarnings("all")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE) 
@JsonPropertyOrder({
    "locale",
    "currency",
    "affiliateCode",
    "brand",
    "sessionId"
})
public class GetQuestionsRequestType implements SpecificRecord, Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"GetQuestionsRequestType\",\"namespace\":\"" + GetQuestionsRequestType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"locale\",\"type\":[\"string\",\"null\"]},{\"name\":\"currency\",\"type\":[\"string\",\"null\"]},{\"name\":\"affiliateCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"brand\",\"type\":[\"string\",\"null\"]},{\"name\":\"sessionId\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public GetQuestionsRequestType(
        String locale,
        String currency,
        String affiliateCode,
        String brand,
        String sessionId) {
        this.locale = locale;
        this.currency = currency;
        this.affiliateCode = affiliateCode;
        this.brand = brand;
        this.sessionId = sessionId;
    }

    public GetQuestionsRequestType() {
    }

    /**
     * 语言地区，例如 "en-US"
     */
    @JsonProperty("Locale") 
    private String locale;

    /**
     * 货币代码，例如 "USD"
     */
    @JsonProperty("Currency") 
    private String currency;

    /**
     * 渠道代码
     */
    @JsonProperty("AffiliateCode") 
    private String affiliateCode;

    /**
     * 品牌代码
     */
    @JsonProperty("Brand") 
    private String brand;

    /**
     * 会话ID
     */
    @JsonProperty("SessionId") 
    private String sessionId;

    /**
     * 语言地区，例如 "en-US"
     */
    public String getLocale() {
        return locale;
    }

    /**
     * 语言地区，例如 "en-US"
     */
    public void setLocale(final String locale) {
        this.locale = locale;
    }

    /**
     * 货币代码，例如 "USD"
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 货币代码，例如 "USD"
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * 渠道代码
     */
    public String getAffiliateCode() {
        return affiliateCode;
    }

    /**
     * 渠道代码
     */
    public void setAffiliateCode(final String affiliateCode) {
        this.affiliateCode = affiliateCode;
    }

    /**
     * 品牌代码
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 品牌代码
     */
    public void setBrand(final String brand) {
        this.brand = brand;
    }

    /**
     * 会话ID
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * 会话ID
     */
    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.locale;
            case 1: return this.currency;
            case 2: return this.affiliateCode;
            case 3: return this.brand;
            case 4: return this.sessionId;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.locale = (String)fieldValue; break;
            case 1: this.currency = (String)fieldValue; break;
            case 2: this.affiliateCode = (String)fieldValue; break;
            case 3: this.brand = (String)fieldValue; break;
            case 4: this.sessionId = (String)fieldValue; break;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in put()");
        }
    }

    @Override
    public Object get(String fieldName) {
        Schema schema = getSchema();
        if (!(schema instanceof RecordSchema)) {
            return null;
        }
        Field field = ((RecordSchema) schema).getField(fieldName);
        return field != null ? get(field.getPos()) : null;
    }

    @Override
    public void put(String fieldName, Object fieldValue) {
        Schema schema = getSchema();
        if (!(schema instanceof RecordSchema)) {
            return;
        }
        Field field = ((RecordSchema) schema).getField(fieldName);
        if (field != null) {
            put(field.getPos(), fieldValue);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final GetQuestionsRequestType other = (GetQuestionsRequestType)obj;
        return 
            Objects.equal(this.locale, other.locale) &&
            Objects.equal(this.currency, other.currency) &&
            Objects.equal(this.affiliateCode, other.affiliateCode) &&
            Objects.equal(this.brand, other.brand) &&
            Objects.equal(this.sessionId, other.sessionId);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.locale == null ? 0 : this.locale.hashCode());
        result = 31 * result + (this.currency == null ? 0 : this.currency.hashCode());
        result = 31 * result + (this.affiliateCode == null ? 0 : this.affiliateCode.hashCode());
        result = 31 * result + (this.brand == null ? 0 : this.brand.hashCode());
        result = 31 * result + (this.sessionId == null ? 0 : this.sessionId.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("locale", locale)
            .add("currency", currency)
            .add("affiliateCode", affiliateCode)
            .add("brand", brand)
            .add("sessionId", sessionId)
            .toString();
    }
}
