package com.example.rainboot.trunk.user.service;

import com.example.rainboot.trunk.user.model.UserUser;

import java.util.List;

/**
 * @Author 小熊
 * @version 1.0
 */
public interface UserService {

    /**
     * 根据id查找指定用户
     * @param id 用户id
     * @return 用户对象
     */
    UserUser findUserById(long id);

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户VO对象
     */
    UserUser findByUsername(String username);

    /**
     * 注册用户
     * @param user 用户参数
     * @return 是否成功
     */
    boolean addUser(UserUser user);
}
