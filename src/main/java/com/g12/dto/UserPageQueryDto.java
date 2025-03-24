package com.g12.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPageQueryDto implements Serializable {

    //页数
    int page;

    //每页的大小
    int pageSize;

    //用户名
    String username;
}
