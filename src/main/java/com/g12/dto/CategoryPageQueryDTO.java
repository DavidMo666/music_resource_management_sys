package com.g12.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageQueryDTO {

    private String name;

    private Integer page;

    private Integer pageSize;

    private String sortBy;

    private String sortOrder;

    private Long userId;//此歌单所属的用户id
}