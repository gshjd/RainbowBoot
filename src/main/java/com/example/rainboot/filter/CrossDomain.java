package com.example.rainboot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问请求处理器
 */
@Configuration
public class CrossDomain implements Filter {

    private Logger logger = LoggerFactory.getLogger(CrossDomain.class);

    /**
     * 健康服务白名单ip
     */
    @Value("${health-ip}")
    private String healthIp;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 解决跨域问题
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, Accept,token");
        // 用户是否访问健康服务
        // 健康服务结尾路径
        String healthEnds = "/health";
        if (request.getRequestURL().toString().endsWith(healthEnds)) {
            // 用户ip是否允许访问健康服务
            if (!accessToHealth(request)) {
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    /**
     * 检查是否是健康检查路径
     * ip是否是白名单
     *
     * @param request request
     * @return 是否允许访问健康服务
     */
    public boolean accessToHealth(HttpServletRequest request) {
        String userIp = getIpAddress(request);
        // 用户ip
        String[] ipArray = ",".split(healthIp);
        // 访问ip和符合白名单ip，则放行
        for (String ip : ipArray) {
            if (userIp.equals(ip)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取用户ip
     *
     * @param request request
     * @return 用户ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        // ip未知
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
