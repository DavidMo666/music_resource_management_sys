package com.g12.service;

import com.g12.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryService {

    int deleteCategory(Long categoryId);
}
