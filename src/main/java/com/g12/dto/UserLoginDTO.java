package com.g12.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    public String email;

    public String password;

    public String verKey;//verkey

    public String code;//用户输入的验证码
}
