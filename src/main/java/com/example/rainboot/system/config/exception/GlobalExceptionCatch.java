package com.example.rainboot.system.config.exception;

import com.example.rainboot.system.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeoutException;

/**
 * 全局异常捕捉
 *
 * @Author 小熊
 * @Created 2019-07-12 4:15 PM
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionCatch {
    @ResponseBody
    @ExceptionHandler(value = TimeoutException.class)
    public ApiResult<T> exceptionHandler(TimeoutException e) {
        log.error("访问超时：", e);
        return ApiResult.error(10000, "访问超时");
    }
}
