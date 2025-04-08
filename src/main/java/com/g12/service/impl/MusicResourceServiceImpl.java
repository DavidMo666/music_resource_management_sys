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

import java.util.List;

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

    @Override
    public int batchDeleteResources(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        // 调用 Mapper 层的方法执行批量删除操作
        return musicResourceMapper.batchDeleteResources(ids);
    }
}
