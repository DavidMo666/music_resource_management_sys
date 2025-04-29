package com.g12.vo;

import lombok.Data;

@Data
public class DailyUserCountVO {
    private String date;  // 日期（格式：yyyy-MM-dd）
    private Integer count; // 当日新增用户数
}