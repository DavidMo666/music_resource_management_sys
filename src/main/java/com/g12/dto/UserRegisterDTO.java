package com.g12.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    private String name;

    //头像
    private String avatar;

    //密码
    private String password;

    //邮箱
    private String email;
}
