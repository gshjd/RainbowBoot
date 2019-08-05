package com.example.rainboot.trunk.user.mapper;

import com.example.rainboot.trunk.user.model.UserUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据id查找指定用户
     * @param id 用户id
     * @return 用户信息
     */
    UserUser findUserById(long id);

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    UserUser findByUsername(String username);
}
