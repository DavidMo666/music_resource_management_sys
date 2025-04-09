package com.g12.service.impl;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.g12.mapper.CategoryMapper;
import com.g12.result.PageResult;
import com.g12.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {

        //对排序设置默认值
        if (categoryPageQueryDTO.getSortBy() == null){
            categoryPageQueryDTO.setSortBy("createTime");
            categoryPageQueryDTO.setSortOrder("DESC");
        }

        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        Page<MusicCategory> pages = categoryMapper.pageQuery(categoryPageQueryDTO);

        PageResult pageResult = new PageResult(pages.getTotal(), pages.getResult());

        return pageResult;
    }
}
