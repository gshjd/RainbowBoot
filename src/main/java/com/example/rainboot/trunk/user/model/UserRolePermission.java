package com.example.rainboot.trunk.user.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限关联表
 * @Author 小熊
 * @Created 2019-07-15
 */
@Data
@ToString
public class UserRolePermission implements Serializable {

    private static final long serialVersionUID = 3426017581301723510L;
    private Long id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 权限id
     */
    private Long permissionId;
    private Date createTime;
    private Date updateTime;
}
