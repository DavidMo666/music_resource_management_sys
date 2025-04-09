package com.g12.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicResourcePageQueryDTO {

    Integer type;//音乐类型种类

    Integer page;

    Integer pageSize;

    Integer status;

    String sortOrder;//asc/desc

    String sortBy;//根据什么来排序 升序/降序

}
