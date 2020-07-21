package com.example.rainboot.config.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.rainboot.common.util.AESUtil;
import com.example.rainboot.common.util.JedisUtil;
import com.example.rainboot.trunk.user.model.UserUser;
import com.example.rainboot.trunk.user.model.vo.UserUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小熊
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private Jedis jedis = JedisUtil.getJedis();

    @Value("${spring.redis.jedis.renewal-time}")
    private int renewalTime;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {

        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有LoginToken注释，有则跳过认证
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (checkToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token");
                }
                // 获取 token 中的 user id
                String username;
                try {
                    // 解密token
                    token = AESUtil.decode(token);
                    if (token == null) {
                        throw new RuntimeException("Token异常,非法访问！");
                    }
                    username = JWT.decode(token).getClaim("username").asString();
                    if (StringUtils.isEmpty(username)) {
                        throw new RuntimeException("用户不存在，请重新登录");
                    }

                    // 如果缓存中没拿到用户信息，说明用户信息已经过期
                    String userValue = jedis.get(username);
                    if (StringUtils.isEmpty(userValue)) {
                        throw new RuntimeException("用户已过期, 请重新登录");
                    }

                    UserUserVO user = JSON.parseObject(userValue, UserUserVO.class);
                    // 用户信息缓存续约
                    jedis.expire(user.getUsername(), renewalTime);
                    Boolean verify = JwtUtil.isVerify(token, user);
                    if (!verify) {
                        throw new RuntimeException("非法访问!");
                    }
                    // 用户权限
                    List<String> userPermissions = user.getPermissions();
//                    方法所属权限
                    String[] permissions = checkToken.value().split(",");
                    // 权限匹配
                    boolean perFlag = true;
                    // 方法是否开放
                    if (StringUtils.equals("", permissions[0])) {
                        return true;
                    }
                    // 用户权限为null，分配内存空间
                    if (userPermissions == null) {
                        userPermissions = new ArrayList<>();
                    }
                    for (String userPermission :
                            userPermissions) {
                        for (String permission :
                                permissions) {
                            if (StringUtils.equals(userPermission, permission)) {
                                perFlag = false;
                            }
                        }
                    }
                    if (perFlag) {
                        throw new RuntimeException("没有权限！");
                    }
                    return true;
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("访问异常！");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }

}
