package com.example.rainboot.trunk.user.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 * @Author 小熊
 * @Created 2019-07-15
 */
@Entity
@Data
@ToString
public class UserRole implements Serializable {
    private static final long serialVersionUID = -3110962548405495601L;
    @Id
    private Long id;
    private String roleName;
    private Date createTime;
    private Date updateTime;
}
