package com.g12.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicCategory {

    public Long id;

    public Long userId;//用户id

    public String name;//分类名称

    public String description;//分类描述

    public String cover;

    public LocalDateTime createTime;

    public LocalDateTime updateTime;
}
