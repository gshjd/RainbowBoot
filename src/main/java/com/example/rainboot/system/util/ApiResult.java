package com.example.rainboot.system.util;

import java.io.Serializable;

/**
 * api restful 返回封装类
 *
 * @param <T>
 * @author xiaoxiong
 */
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 7694862797861801164L;

    private final int code;
    private final T data;
    private final String msg;
    private final Long timestamp;

    private ApiResult(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    private ApiResult(int code, T data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    private ApiResult(T data, String msg) {
        this.code = 200;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(data);
    }

    public static <T> ApiResult<T> success(T data, String msg) {
        return new ApiResult<>(data, msg);
    }

    public static <T> ApiResult<T> error(int code, T data, String msg) {
        return new ApiResult<>(code, data, msg);
    }

    public static <T> ApiResult<T> error(int code, String msg) {
        return new ApiResult<>(code, null, msg);
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
