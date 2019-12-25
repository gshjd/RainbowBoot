package com.example.rainboot.trunk.user.model.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 6593188171829480128L;
    private Long id;
    private String username;
    private String password;
    private Date gmtCreated;
    private Date gmtModified;
}
