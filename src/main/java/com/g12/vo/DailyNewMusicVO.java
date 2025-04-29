package com.g12.vo;

import lombok.Data;

@Data
public class DailyNewMusicVO {
    private String date;  // 日期（格式：yyyy-MM-dd）
    private Integer count;
}
