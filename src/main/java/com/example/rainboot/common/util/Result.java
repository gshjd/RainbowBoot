package com.example.rainboot.common.util;

import java.io.Serializable;

/**
 * RESTFUL风格代码
 *
 * @Author 小熊
 * @Created 2018-12-24
 * @Remark 无
 */
public class Result<T> implements Serializable {

    /**
     * 状态值
     */
    private Integer code;
    /**
     * 数据
     */
    private T data;
    /**
     * 反馈消息
     */
    private String message;

    /**
     * 服务端时间戳
     */
    private Long timestamp;

    private Result(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Result<T> success() {
        return new Result<>(200, null, "success");
    }

    public static <T> Result<T> success(T data) {
        return new <T> Result<T>(200, data, "");
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(200, null, message);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(200, data, message);
    }

    public static <T> Result<T> success(Integer code, T data, String message) {
        return new Result<>(code, data, message);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, null, message);
    }

    public static <T> Result<T> error() {
        return new Result<>(500, null, "error");
    }

    public static <T> Result<T> error(T data) {
        return new Result<>(500, data, "");
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(500, null, message);
    }

    public static <T> Result<T> error(T data, String message) {
        return new Result<>(500, data, message);
    }

    public static <T> Result<T> error(Integer code, T data, String message) {
        return new Result<>(code, data, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
