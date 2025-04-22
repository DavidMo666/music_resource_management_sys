package com.g12.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class Admin {
    private Long id;
    private String username;
    private String password;  // 假设数据库中存储的是MD5加密后的密码
}