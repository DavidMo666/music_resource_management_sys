package com.g12.service;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.result.PageResult;

public interface CategoryService {

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
}
