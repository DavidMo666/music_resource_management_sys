package com.g12.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsVO {
    private Long userCount;
    private Long musicResourceCount;
}