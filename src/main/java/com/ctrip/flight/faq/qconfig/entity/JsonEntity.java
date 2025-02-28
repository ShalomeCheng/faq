package com.ctrip.flight.faq.qconfig.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonEntity {

    private String title;
    private List<Data> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{title='%s', data=%s}", title, data);
    }

    static class Data {
        @JsonProperty("field.int")
        private Integer intField;
        @JsonProperty("field.text")
        private String textField;

        public Integer getIntField() {
            return intField;
        }

        public void setIntField(Integer intField) {
            this.intField = intField;
        }

        public String getTextField() {
            return textField;
        }

        public void setTextField(String textField) {
            this.textField = textField;
        }

        @Override
        public String toString() {
            return String.format("{intField=%s, textField='%s'}", intField, textField);
        }
    }
}
