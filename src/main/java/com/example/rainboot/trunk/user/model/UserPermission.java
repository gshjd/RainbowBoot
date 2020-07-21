package com.example.rainboot.trunk.user.model;


import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 权限表
 * @Author 小熊
 * @Created 2019-07-11 03:19 PM
 */
@Entity
@Data
@ToString
public class UserPermission {

  @Id
  private Long id;
  /**
   * 权限名称
   */
  private String permissions;
  /**
   * 权限描述
   */
  private String description;
  private Date createTime;
  private Date updateTime;
}
