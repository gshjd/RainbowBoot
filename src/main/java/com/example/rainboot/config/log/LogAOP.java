package com.example.rainboot.config.log;

import com.example.rainboot.config.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.SpringBootConfiguration;
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
@SpringBootConfiguration
public class LogAOP {
    /**
     * controller包或子包下任意方法
     */
    private static final String EXECUTION_CONTROLLER = "execution(* com.example.rainboot.trunk.*.controller..*(..))";

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
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        StringBuilder logStr = new StringBuilder("Access request：");
        logStr.append(joinPoint.getSignature());
        if (token != null) {
            logStr.append(",user：");
            logStr.append(JwtUtil.getUsername(token));
        }
        log.info(logStr.toString());
    }

}
