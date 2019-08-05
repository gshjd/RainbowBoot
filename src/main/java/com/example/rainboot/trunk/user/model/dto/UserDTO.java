package com.example.rainboot.trunk.user.model.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 6593188171829480128L;
    @Excel(name = "id")
    private Long id;
    @Excel(name = "username")
    private String username;
    @Excel(name = "password")
    private String password;
    @Excel(name = "gmtCreated")
    private Date gmtCreated;
    @Excel(name = "gmtModified")
    private Date gmtModified;
}
