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
import java.util.List;

/**
 * FAQ 响应数据
 */
@SuppressWarnings("all")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE) 
@JsonPropertyOrder({
    "categories"
})
public class TripFaqResponse implements SpecificRecord, Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"TripFaqResponse\",\"namespace\":\"" + TripFaqResponse.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"categories\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"FaqCategoryType\",\"namespace\":\"" + FaqCategoryType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"string\",\"null\"]},{\"name\":\"name\",\"type\":[\"string\",\"null\"]},{\"name\":\"description\",\"type\":[\"string\",\"null\"]},{\"name\":\"questions\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"FaqQuestionType\",\"namespace\":\"" + FaqQuestionType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"string\",\"null\"]},{\"name\":\"title\",\"type\":[\"string\",\"null\"]},{\"name\":\"content\",\"type\":[\"string\",\"null\"]},{\"name\":\"tags\",\"type\":[{\"type\":\"array\",\"items\":\"string\"},\"null\"]},{\"name\":\"updatedAt\",\"type\":[\"long\",\"null\"]}]}},\"null\"]}]}},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public TripFaqResponse(
        List<FaqCategoryType> categories) {
        this.categories = categories;
    }

    public TripFaqResponse() {
    }

    /**
     * FAQ 分类列表
     */
    @JsonProperty("categories") 
    private List<FaqCategoryType> categories;

    /**
     * FAQ 分类列表
     */
    public List<FaqCategoryType> getCategories() {
        return categories;
    }

    /**
     * FAQ 分类列表
     */
    public void setCategories(final List<FaqCategoryType> categories) {
        this.categories = categories;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.categories;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.categories = (List<FaqCategoryType>)fieldValue; break;
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

        final TripFaqResponse other = (TripFaqResponse)obj;
        return 
            Objects.equal(this.categories, other.categories);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.categories == null ? 0 : this.categories.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("categories", categories)
            .toString();
    }
}
