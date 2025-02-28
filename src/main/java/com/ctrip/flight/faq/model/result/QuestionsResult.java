package com.ctrip.flight.faq.model.result;

/**
 * FAQ 查询结果封装类
 * @param <T> 响应数据类型
 */
public record QuestionsResult<T>(
    boolean success,
    T value,
    String failureMessage
) {
    /**
     * 创建成功结果
     * @param value 响应数据
     * @return QuestionsResult<T>
     * @param <T> 响应数据类型
     */
    public static <T> QuestionsResult<T> success(T value) {
        return new QuestionsResult<>(true, value, null);
    }

    /**
     * 创建失败结果
     * @param message 失败信息
     * @return QuestionsResult<T>
     * @param <T> 响应数据类型
     */
    public static <T> QuestionsResult<T> failure(String message) {
        return new QuestionsResult<>(false, null, message);
    }

    /**
     * 判断是否成功
     * @return boolean
     */
    public boolean isSuccess() {
        return success;
    }
} 