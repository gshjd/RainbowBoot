package com.example.rainboot.trunk.user.service.impl;

import com.example.rainboot.trunk.user.mapper.UserMapper;
import com.example.rainboot.trunk.user.model.UserUser;
import com.example.rainboot.trunk.user.reposity.UserReposity;
import com.example.rainboot.trunk.user.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author 小熊
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserReposity userReposity;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserReposity userReposity) {
        this.userMapper = userMapper;
        this.userReposity = userReposity;
    }


    @Override
    public UserUser findUserById(long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public UserUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean addUser(UserUser user) {
        return ObjectUtils.isNotEmpty(userReposity.save(user));
    }

    /**
     * 添加用户默认权限
     * @param userId 用户id
     * @return 是否添加成功
     */
//    private boolean addPermission(long userId) {
//        Permissions permissions = new Permissions();
//        permissions.setId(KeyUtil.getKey());
//        permissions.setPermissions("");
//        permissions.setDescription("");
//        permissions.setUserId(userId);
//        permissions.setGmtCreated(new Date());
//        permissions.setGmtModified(new Date());
//    }


}
