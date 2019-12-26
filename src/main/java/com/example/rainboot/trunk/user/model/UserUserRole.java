package com.example.rainboot.trunk.user.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 * @Author 小熊
 * @Created 2019-07-15
 */
@Entity
@Data
@ToString
public class UserUserRole implements Serializable {
    private static final long serialVersionUID = 832523829952915695L;
    @Id
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;
    private Date createTime;
    private Date updateTime;

}
