package com.g12.service;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.result.PageResult;

public interface MusicResourceService {

    /**
     * 音乐资源分页查询
     * @param musicResourcePageQueryDTO
     * @return
     */
    PageResult pageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO);

}
