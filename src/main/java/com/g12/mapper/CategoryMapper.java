package com.g12.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface CategoryMapper {
    @Delete("DELETE FROM musiccategory WHERE id = #{id}")
    int deleteCategory(@Param("id") Integer id);
}
