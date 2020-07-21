package com.example.rainboot.config.log;

import com.example.rainboot.common.util.KeyUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogMdcInterceptor implements HandlerInterceptor {

    /**
     * 日志跟踪id名。
     */
    private static final String TRACE_ID = "traceId";

    /**
     * 用户ip跟踪
     */
    private static final String USER_IP = "userIp";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        MDC.put(LogMdcInterceptor.TRACE_ID, KeyUtil.getKey().toString());
        MDC.put(LogMdcInterceptor.USER_IP, httpServletRequest.getRemoteAddr());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
        MDC.remove(LogMdcInterceptor.TRACE_ID);
        MDC.remove(LogMdcInterceptor.USER_IP);
    }

}
