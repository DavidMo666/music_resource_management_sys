package com.g12.service;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.mapper.CategoryMapper;
import com.g12.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryService {

    int deleteCategory(Long categoryId);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
}
