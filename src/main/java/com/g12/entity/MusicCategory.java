package com.g12.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicCategory {

    public Long id;

    public String name;//分类名称

    public String description;//分类描述
}
