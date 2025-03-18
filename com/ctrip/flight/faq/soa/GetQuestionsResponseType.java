package com.ctrip.flight.faq.soa;

import com.ctriposs.baiji.exception.BaijiRuntimeException;
import com.ctriposs.baiji.rpc.common.*;
import com.ctriposs.baiji.rpc.common.types.ResponseStatusType;
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
 * FAQ 查询响应类型
 */
@SuppressWarnings("all")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE) 
@JsonPropertyOrder({
    "data",
    "responseStatus"
})
public class GetQuestionsResponseType implements SpecificRecord, HasResponseStatus, Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"GetQuestionsResponseType\",\"namespace\":\"" + GetQuestionsResponseType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"data\",\"type\":[{\"type\":\"record\",\"name\":\"TripFaqResponse\",\"namespace\":\"" + TripFaqResponse.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"categories\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"FaqCategoryType\",\"namespace\":\"" + FaqCategoryType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"string\",\"null\"]},{\"name\":\"name\",\"type\":[\"string\",\"null\"]},{\"name\":\"description\",\"type\":[\"string\",\"null\"]},{\"name\":\"questions\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"FaqQuestionType\",\"namespace\":\"" + FaqQuestionType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"string\",\"null\"]},{\"name\":\"title\",\"type\":[\"string\",\"null\"]},{\"name\":\"content\",\"type\":[\"string\",\"null\"]},{\"name\":\"tags\",\"type\":[{\"type\":\"array\",\"items\":\"string\"},\"null\"]},{\"name\":\"updatedAt\",\"type\":[\"long\",\"null\"]}]}},\"null\"]}]}},\"null\"]}]},\"null\"]},{\"name\":\"ResponseStatus\",\"type\":[{\"type\":\"record\",\"name\":\"ResponseStatusType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Timestamp\",\"type\":[\"datetime\",\"null\"]},{\"name\":\"Ack\",\"type\":[{\"type\":\"enum\",\"name\":\"AckCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"Success\",\"Failure\",\"Warning\",\"PartialFailure\"]},\"null\"]},{\"name\":\"Errors\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ErrorDataType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Message\",\"type\":[\"string\",\"null\"]},{\"name\":\"ErrorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"StackTrace\",\"type\":[\"string\",\"null\"]},{\"name\":\"SeverityCode\",\"type\":[{\"type\":\"enum\",\"name\":\"SeverityCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"Error\",\"Warning\"]},\"null\"]},{\"name\":\"ErrorFields\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ErrorFieldType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"FieldName\",\"type\":[\"string\",\"null\"]},{\"name\":\"ErrorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"Message\",\"type\":[\"string\",\"null\"]}]}},\"null\"]},{\"name\":\"ErrorClassification\",\"type\":[{\"type\":\"enum\",\"name\":\"ErrorClassificationCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"ServiceError\",\"ValidationError\",\"FrameworkError\",\"SLAError\",\"SecurityError\"]},\"null\"]}]}},\"null\"]},{\"name\":\"Build\",\"type\":[\"string\",\"null\"]},{\"name\":\"Version\",\"type\":[\"string\",\"null\"]},{\"name\":\"Extension\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ExtensionType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Id\",\"type\":[\"string\",\"null\"]},{\"name\":\"Version\",\"type\":[\"string\",\"null\"]},{\"name\":\"ContentType\",\"type\":[\"string\",\"null\"]},{\"name\":\"Value\",\"type\":[\"string\",\"null\"]}]}},\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public GetQuestionsResponseType(
        TripFaqResponse data,
        ResponseStatusType responseStatus) {
        this.data = data;
        this.responseStatus = responseStatus;
    }

    public GetQuestionsResponseType() {
    }

    /**
     * 响应数据
     */
    @JsonProperty("Data") 
    private TripFaqResponse data;

    /**
     * 响应状态
     */
    @JsonProperty("ResponseStatus") 
    private ResponseStatusType responseStatus;

    /**
     * 响应数据
     */
    public TripFaqResponse getData() {
        return data;
    }

    /**
     * 响应数据
     */
    public void setData(final TripFaqResponse data) {
        this.data = data;
    }

    /**
     * 响应状态
     */
    public ResponseStatusType getResponseStatus() {
        return responseStatus;
    }

    /**
     * 响应状态
     */
    public void setResponseStatus(final ResponseStatusType responseStatus) {
        this.responseStatus = responseStatus;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.data;
            case 1: return this.responseStatus;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.data = (TripFaqResponse)fieldValue; break;
            case 1: this.responseStatus = (ResponseStatusType)fieldValue; break;
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

        final GetQuestionsResponseType other = (GetQuestionsResponseType)obj;
        return 
            Objects.equal(this.data, other.data) &&
            Objects.equal(this.responseStatus, other.responseStatus);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.data == null ? 0 : this.data.hashCode());
        result = 31 * result + (this.responseStatus == null ? 0 : this.responseStatus.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("data", data)
            .add("responseStatus", responseStatus)
            .toString();
    }
}
