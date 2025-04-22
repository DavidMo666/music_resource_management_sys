package com.g12.mapper;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.g12.entity.MusicResource;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Delete("DELETE FROM musiccategory WHERE id = #{id}")
    int deleteCategory(@Param("id") Integer id);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<MusicCategory> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     * @param category 分类信息
     * @return 操作结果
     */
    void insert(MusicCategory category);

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
    List<MusicResource> getMusicInCategory(Long categoryId);
}
