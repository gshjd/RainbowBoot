package com.example.rainboot.trunk.user.model.vo;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author 小熊
 * @Created 2019-07-15
 */
@Data
@ToString
public class UserUserVO implements Serializable {
  private static final long serialVersionUID = -8470021798915920728L;
  private Long id;
  private String username;
  private String password;
  private Date createTime;
  private Date updateTime;
  private List<String> permissions;
}
