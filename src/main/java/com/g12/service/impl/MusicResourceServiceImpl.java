package com.g12.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.g12.mapper.MusicResourceMapper;
import com.g12.result.PageResult;
import com.g12.service.MusicResourceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    
    /**
     * 根据用户ID查询音乐资源
     */
    @Override
    public PageResult listByUserId(Integer userId) {
        try {
            List<MusicResource> records = musicResourceMapper.selectByUserId(userId);
            if (records == null || records.isEmpty()) {
                return new PageResult(0L, Collections.emptyList());
            }
            return new PageResult((long) records.size(), records);
        } catch (Exception e) {
            log.error("根据用户ID查询音乐资源失败，用户ID: {}", userId, e);
            return new PageResult(0L, Collections.emptyList());
        }
    }

    /**
     * 根据音乐名称查询音乐资源
     */
    @Override
    public PageResult listByName(String name) {
        try {
            List<MusicResource> records = musicResourceMapper.selectByName(name);
            if (records == null || records.isEmpty()) {
                return new PageResult(0L, Collections.emptyList());
            }
            return new PageResult((long) records.size(), records);
        } catch (Exception e) {
            log.error("根据名称查询音乐资源失败，名称: {}", name, e);
            return new PageResult(0L, Collections.emptyList());
        }
    }

    /**
     * 组合查询音乐资源
     */
    @Override
    public PageResult listByCondition(Integer userId, String name) {
        try {
            List<MusicResource> records = musicResourceMapper.selectByCondition(userId, name);
            if (records == null || records.isEmpty()) {
                return new PageResult(0L, Collections.emptyList());
            }
            return new PageResult((long) records.size(), records);
        } catch (Exception e) {
            log.error("组合查询音乐资源失败，用户ID: {}, 名称: {}", userId, name, e);
            return new PageResult(0L, Collections.emptyList());
        }
    }

    /**
     * 更新音乐资源状态
     * @param status 状态（0-封禁，1-正常）
     * @param id 音乐资源ID
     * @return 是否更新成功
     */
    @Override
    public boolean updateStatus(Integer status, Integer id) {
        try {
            // 先检查记录是否存在
            MusicResource resource = musicResourceMapper.selectById(id);
            if (resource == null) {
                return false;
            }
            
            // 执行更新
            return musicResourceMapper.updateStatus(status, id);
        } catch (Exception e) {
            log.error("更新音乐资源状态失败，status: {}, id: {}, error: {}", status, id, e.getMessage());
            throw new RuntimeException("更新音乐资源状态失败", e);
        }
    }
}
