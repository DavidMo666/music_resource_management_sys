package com.g12.service.impl;

import com.g12.entity.MusicCategory;
import com.g12.mapper.CategoryMapper;
import com.g12.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void save(MusicCategory category) {
        categoryMapper.insert(category);
    }
}