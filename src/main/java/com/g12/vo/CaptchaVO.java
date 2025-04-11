package com.g12.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVO {

    public String verKey; //后端生成的随机key

    public String verCode; //图形验证码
}