package com.g12.service.impl;

import java.time.LocalDateTime;
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
    private CategoryMapper categoryMapper;

    @Override
    public int deleteCategory(Long categoryId) {
        // 可以在这里添加业务逻辑，比如检查关联资源
        return categoryMapper.deleteCategory(Math.toIntExact(categoryId));
    }

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

    /**
     * 新增分类
     * @param category 分类信息
     * @return 操作结果
     */
    @Override
    public void save(MusicCategory category) {
        categoryMapper.insert(category);
    }

    @Override
    public void update(MusicCategory category) {
        // 设置更新时间
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

}
