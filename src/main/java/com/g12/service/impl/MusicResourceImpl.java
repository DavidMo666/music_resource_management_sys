package com.g12.service.impl;

import com.g12.mapper.MusicResourceMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.MusicResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicResourceImpl implements MusicResource {

    // 自动注入 MusicResourceMapper 来执行数据库操作
    @Autowired
    private MusicResourceMapper musicResourceMapper;

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
     * @param userId 用户ID
     * @return 音乐资源列表
     */
    @Override
    public PageResult listByUserId(Integer userId) {
        List<com.g12.entity.MusicResource> records = musicResourceMapper.selectByUserId(userId);
        return new PageResult((long) records.size(), records);
    }

    /**
     * 根据音乐名称查询音乐资源
     * @param name 音乐名称
     * @return 分页结果
     */
    @Override
    public PageResult listByName(String name) {
        List<com.g12.entity.MusicResource> records = musicResourceMapper.selectByName(name);
        return new PageResult((long) records.size(), records);
    }

    /**
     * 组合查询音乐资源
     * @param userId 用户ID
     * @param name 音乐名称
     * @return 分页结果
     */
    @Override
    public PageResult listByCondition(Integer userId, String name) {
        List<com.g12.entity.MusicResource> records = musicResourceMapper.selectByCondition(userId, name);
        return new PageResult((long) records.size(), records);
    }
}
