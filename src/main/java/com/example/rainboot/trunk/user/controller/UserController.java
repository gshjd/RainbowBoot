package com.example.rainboot.trunk.user.controller;

import com.alibaba.fastjson.JSON;
import com.example.rainboot.common.util.*;
import com.example.rainboot.config.jwt.CheckToken;
import com.example.rainboot.config.jwt.JwtUtil;
import com.example.rainboot.config.jwt.LoginToken;
import com.example.rainboot.trunk.user.model.UserUser;
import com.example.rainboot.trunk.user.model.dto.UserDTO;
import com.example.rainboot.trunk.user.model.vo.UserUserVO;
import com.example.rainboot.trunk.user.service.PermissionService;
import com.example.rainboot.trunk.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户类
 *
 * @author HandlyLee
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Value("${spring.redis.jedis.renewal-time}")
    private final int renewalTime;

    private final UserService userService;
    private final PermissionService permissionService;

    @Autowired
    public UserController(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
        renewalTime = 0;
    }

    /**
     * 登陆
     *
     * @param user 用户
     * @return token
     */
    @GetMapping("/login")
    @LoginToken
    public Result login(UserUserVO user) {
        UserUser userUser = userService.findByUsername(user.getUsername());
        if (userUser == null || !BCrypt.checkpw(user.getPassword(), userUser.getPassword())) {
            log.error("登录失败,账号或密码错误，尝试登陆账户：" + user.getUsername());
            return Result.error("登录失败,账号或密码错误");
        } else {
            // 查询用户权限，注入权限
            List<String> permissions = permissionService.findPermissionIdByUserId(userUser.getId());
            // 对象注入
            UserUserVO userUserVO = new UserUserVO();
            userUserVO.setUsername(userUser.getUsername());
            userUserVO.setPassword(userUser.getPassword());
            String token = JwtUtil.createJWT(userUserVO);
            userUserVO.setPermissions(permissions);

            // 用户加入缓存
            String userStr = JSON.toJSONString(userUserVO);
            Jedis jedis = JedisUtil.getJedis();
            jedis.set(userUser.getUsername(), userStr);
            // 用户信息30分钟有效期
            jedis.expire(userUser.getUsername(), renewalTime);
            return Result.success(token, null);
        }
    }

    /**
     * 用户信息30分钟有效期
     *
     * @param username 用户名
     * @return 是否续约成功
     */
    @GetMapping("expire")
    public Result expire(String username) {
        Jedis jedis = JedisUtil.getJedis();
        if (StringUtils.isEmpty(jedis.get(username))) {
            return Result.error(101, "续约失败，用户已过期或不存在");
        }
        // 用户信息30分钟有效期
        Long count = jedis.expire(username, renewalTime);
        if (count > 0) {
            return Result.success("续约成功");
        }
        return Result.error("续约失败");
    }

    /**
     * 查看个人信息
     *
     * @return 你已通过验证
     */
    @CheckToken("getMessage")
    @GetMapping("/getMessage")
    public Result getMessage() {
        return Result.success("你已通过验证");
    }

    /**
     * 注册用户
     *
     * @param userDTO 注册参数
     * @return 是否注册成功
     */
    @PostMapping("addUser")
    public Result addUser(UserDTO userDTO) {
        // 用户信息
        UserUser user = new UserUser();
        user.setId(KeyUtil.getKey());
        user.setUsername(userDTO.getUsername());
        user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(12)));
        user.setGmtCreated(new Date());
        user.setGmtModified(new Date());
        user.setStatus(0);

        boolean suc = userService.addUser(user);
        if (suc) {
            return Result.success("注册成功");
        }
        return Result.error("注册失败");
    }

    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 200000; i1++) {
                    map.put(String.valueOf(i1 + KeyUtil.getKey()), i1);
                }
            }).start();
        }
        Thread.sleep(180 * 1000 * 60);
        System.out.println(map.size());
    }

}