package com.g12.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{

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

    //状态（0:block or 1:unblock）
    private Integer status;

    //性别 0 女 1 男
    private String sex;

    //头像
    private String avatar;

    //注册时间
    private LocalDateTime createTime; // create_time

}
