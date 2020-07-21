package com.example.rainboot.system.config.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 小熊
 * @Created 1018/10/31
 */
@Aspect
@Slf4j
@Configuration
public class LogAOP {
    /**
     * controller包或子包下任意方法
     */
    private static final String EXECUTION_CONTROLLER = "execution(* com.example.rainboot.application.*.controller..*(..))";

    @Pointcut(EXECUTION_CONTROLLER)
    private void controller() {
    }

    /**
     * 拦截请求的controller，打印路径
     *
     * @param joinPoint joinPoint
     */
    @Before(value = "controller()")
    public void before(JoinPoint joinPoint) {
    }

}
