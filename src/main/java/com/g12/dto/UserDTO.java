package com.g12.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id; // user_id

    //姓名
    private String name;

    //用户名
    private String userName; // user_name

    //密码
    private String password;

    //手机号
    private String phone;

    //邮箱
    private String email;

    //性别 0 女 1 男
    private String sex;

    //身份证号
    private String idNumber; // id_number

    //头像
    private String avatar;


}
