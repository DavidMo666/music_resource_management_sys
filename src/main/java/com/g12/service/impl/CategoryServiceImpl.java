package com.g12.service.impl;

import com.g12.mapper.CategoryMapper;
import com.g12.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int deleteCategory(Long categoryId) {
        // 可以在这里添加业务逻辑，比如检查关联资源
        return categoryMapper.deleteCategory(Math.toIntExact(categoryId));
    }
}
