package com.example.rainboot.config.exception;

import com.example.rainboot.common.util.DingTalkUtil;
import com.example.rainboot.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Result exceptionHandler(TimeoutException e) {
        log.error("访问超时：", e);
        return Result.error("访问超时");
    }
}
