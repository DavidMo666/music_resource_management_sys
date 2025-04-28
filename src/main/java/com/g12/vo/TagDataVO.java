package com.g12.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagDataVO {
    private String tagName;   // 标签名称
    private Integer count;    // 对应资源数量
}