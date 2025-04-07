package com.g12.service.impl;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.g12.mapper.MusicResourceMapper;
import com.g12.result.PageResult;
import com.g12.service.MusicResourceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicResourceServiceImpl implements MusicResourceService {

    @Autowired
    MusicResourceMapper musicResourceMapper;

    /**
     * 音乐资源分页查询
     * @param musicResourcePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO) {

        //如果sortOrder 和sortBy为空 设置默认值
        if (musicResourcePageQueryDTO.getSortBy() == null || musicResourcePageQueryDTO.getSortOrder() == null){
            musicResourcePageQueryDTO.setSortBy("id");
            musicResourcePageQueryDTO.setSortOrder("asc");
        }

        PageHelper.startPage(musicResourcePageQueryDTO.getPage(), musicResourcePageQueryDTO.getPageSize());

        Page<MusicResource> pages = musicResourceMapper.pageQuery(musicResourcePageQueryDTO);

        PageResult pageResult = new PageResult(pages.getTotal(), pages.getResult());

        return pageResult;
    }
}
