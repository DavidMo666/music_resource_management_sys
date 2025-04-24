package com.g12.mapper;

import com.g12.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {

    /**
     * 根据tagName查询
     * @param tagName
     * @return
     */
    @Select("select id from tag where name = #{tagName}")
    Long get(String tagName);

    /**
     * 新增tag
     * @param tag
     * @return
     */
    Long addTag(Tag tag);

    /**
     * 获取tags
     * @return
     */
    @Select("select * from tag")
    List<Tag> getTags();

}
