package com.example.rainboot.common.util;

import java.util.Date;

/**
 * RESTFUL风格代码
 *
 * @Author 小熊
 * @Created 2018-12-24
 * @Remark 无
 */
public class Result {

    /**
     * 状态值
     */
    private Integer status;
    /**
     * 数据
     */
    private Object data;
    /**
     * 反馈消息
     */
    private String message;

    /**
     * 服务端时间戳
     */
    private Date timestamp;

    private Result(Integer status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.timestamp = new Date();
    }

    public static Result success() {
        return new Result(200, null, "true");
    }

    public static Result success(Object data) {
        return new Result(200, data, "");
    }

    public static Result success(String message) {
        return new Result(200, null, message);
    }

    public static Result success(Object data, String message) {
        return new Result(200, data, message);
    }

    public static Result success(Integer status, Object data, String message) {
        return new Result(status, data, message);
    }

    public static Result error(Integer status, String message) {
        return new Result(status, null, message);
    }

    public static Result error() {
        return new Result(500, null, "false");
    }

    public static Result error(Object data) {
        return new Result(500, data, "");
    }

    public static Result error(String message) {
        return new Result(500, null, message);
    }

    public static Result error(Object data, String message) {
        return new Result(500, data, message);
    }

    public static Result error(Integer status, Object data, String message) {
        return new Result(status, data, message);
    }

    public Integer getstatus() {
        return status;
    }

    public void setstatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
