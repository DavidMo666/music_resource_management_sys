package com.g12.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPageQueryDTO implements Serializable {

    //页数
    private int page;

    //每页的大小
    private int pageSize;

    //用户名
    private String userName;
}
