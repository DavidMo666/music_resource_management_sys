package com.g12.service;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.g12.entity.MusicResource;
import com.g12.mapper.CategoryMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {

    int deleteCategory(Long categoryId);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     * @param category 分类信息
     * @return 操作结果
     */
    void save(MusicCategory category);

    /**
     * 更新分类信息
     * @param category 分类信息
     */
    void update(MusicCategory category);

    /**
     * 获取歌单里的音乐
     * @param categoryId
     * @return
     */
    Result<List<MusicResource>> getMusicInCategory(Long categoryId);

    /**
     * 增加歌曲到歌单
     * @param categoryId
     * @param musicId
     * @return
     */
    Result addMusicToCategory(Long categoryId, Long musicId);
}
