package com.g12.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MusicResourceMapper {

    /**
     * 批量删除音乐资源
     * @param ids 资源 ID 列表
     * @return 删除的记录数
     */
    int batchDeleteResources(List<Integer> ids);
}
