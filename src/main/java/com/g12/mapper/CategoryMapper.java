package com.g12.mapper;

import com.g12.entity.MusicCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    void insert(MusicCategory category);
}