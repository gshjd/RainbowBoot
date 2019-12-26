package com.example.rainboot.trunk.user.service;


import java.util.List;

public interface PermissionService {

    /**
     * 根据用户id找出所有权限
     * @param userId 用户id
     * @return 用户所有权限
     */
    List<String> findPermissionIdByUserId(long userId);
}
