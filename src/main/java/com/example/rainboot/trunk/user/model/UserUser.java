package com.example.rainboot.trunk.user.model;


import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @Author 小熊
 * @Created 2019-07-15
 */
@Entity
@Data
@ToString
public class UserUser implements Serializable {
  private static final long serialVersionUID = 9125419468207052570L;
  @Id
  private Long id;
  private String username;
  private String password;
  private Date gmtCreated;
  private Date gmtModified;
  private Integer status;

}
