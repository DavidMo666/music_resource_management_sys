package com.g12.mapper;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicResourceMapper {

    /**
     * 音乐资源分页查询
     * @param musicResourcePageQueryDTO
     * @return
     */
    Page<MusicResource> pageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO);
}
