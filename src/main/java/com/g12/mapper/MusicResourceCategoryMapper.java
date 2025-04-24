package com.g12.mapper;

import com.g12.entity.MusicResourceCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 音乐和category表连接的mapper
 */
@Mapper
public interface MusicResourceCategoryMapper {

    @Insert("insert into music_resource_category(category_id, music_id) " +
            "values (#{categoryId},#{musicId},#{createTime})")
    public void addMusicToCategory(MusicResourceCategory musicResourceCategory);
}
