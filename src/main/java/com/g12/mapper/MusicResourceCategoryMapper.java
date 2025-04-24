package com.g12.mapper;

import com.g12.entity.MusicResourceCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 音乐和category表连接的mapper
 */
@Mapper
public interface MusicResourceCategoryMapper {

    @Insert("insert into music_resource_category(category_id, music_id, create_time) " +
            "values (#{categoryId},#{musicId},#{createTime})")
    public void addMusicToCategory(MusicResourceCategory musicResourceCategory);

    @Select("select count(*) from music_resource_system.music_resource_category " +
            "where category_id = #{categoryId} and music_id = #{musicId}")
    int count(MusicResourceCategory mrc);
}
