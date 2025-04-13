package com.g12.mapper;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.github.pagehelper.Page;
import com.g12.entity.MusicCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<MusicCategory> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void insert(MusicCategory category);
}
