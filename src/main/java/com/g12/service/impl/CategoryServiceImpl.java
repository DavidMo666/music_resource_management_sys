package com.g12.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.g12.context.BaseContext;
import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.g12.entity.MusicResource;
import com.g12.entity.MusicResourceCategory;
import com.g12.mapper.CategoryMapper;
import com.g12.mapper.MusicResourceCategoryMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MusicResourceCategoryMapper musicResourceCategoryMapper;

    @Override
    public int deleteCategory(Long categoryId) {
        // 可以在这里添加业务逻辑，比如检查关联资源
        return categoryMapper.deleteCategory(Math.toIntExact(categoryId));
    }

    /**
     * 分页查询 获取当前用户的歌单
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

        //获取用户id
        Long userId = BaseContext.getCurrentId();
        categoryPageQueryDTO.setUserId(userId);

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

        Long userId = BaseContext.getCurrentId();
        category.setUserId(userId);
        category.setCreateTime(LocalDateTime.now());

        categoryMapper.insert(category);
    }

    /**
     * 修改分类
     * @param category 分类信息
     */
    @Override
    public void update(MusicCategory category) {
        // 设置更新时间
        //category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    /**
     * 获取歌单里的音乐
     * @param categoryId
     * @return
     */
    @Override
    public Result<List<MusicResource>> getMusicInCategory(Long categoryId) {

        //1.用catrgoryId 获取连表查询所有音乐资源
        List<MusicResource> list = categoryMapper.getMusicInCategory(categoryId);

        return Result.success(list);
    }

    /**
     * 增加歌曲到歌单
     * @param categoryId
     * @param musicId
     * @return
     */
    @Override
    public Result addMusicToCategory(Long categoryId, Long musicId) {

        MusicResourceCategory mrc = new MusicResourceCategory();
        mrc.setCategoryId(categoryId);
        mrc.setMusicId(musicId);
        mrc.setCreateTime(LocalDateTime.now());

        int count = musicResourceCategoryMapper.count(mrc);
        if (count > 0){
            return Result.error("歌单已有此歌曲");
        }

        musicResourceCategoryMapper.addMusicToCategory(mrc);

        return Result.success();
    }

}
