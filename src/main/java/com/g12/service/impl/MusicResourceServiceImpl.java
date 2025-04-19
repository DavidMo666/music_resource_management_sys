package com.g12.service.impl;

import com.g12.context.BaseContext;
import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.g12.mapper.MusicResourceMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.MusicResourceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
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
    public PageResult adminPageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO) {

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
            throw new RuntimeException("更新音乐资源状态失败", e);
        }
    }

    /**
     * 新增音乐资源
     * @param musicResource 音乐资源信息
     * @return 操作结果
     */
    @Override
    public Result<String> addMusicResource(MusicResource musicResource) {
//        try {
//             验证必要字段
//            if (StringUtils.isEmpty(musicResource.getName())) {
//                return Result.error("音乐名称不能为空");
//            }
//            if (StringUtils.isEmpty(musicResource.getImage())) {
//                return Result.error("音乐封面不能为空");
//            }
//            if (musicResource.getUploadUserId() == null || musicResource.getUploadUserId() <= 0) {
//                return Result.error("上传用户ID无效");
//            }

            // 设置默认值
            if (musicResource.getStatus() == null) {
                musicResource.setStatus(1); // 默认状态为正常
            }
            musicResource.setUploadTime(LocalDateTime.now());

            // 调用Mapper插入数据
            int result = musicResourceMapper.insert(musicResource);
            if (result <= 0) {
                return Result.error("添加音乐资源失败");
            }

            return Result.success("音乐资源添加成功");
//        } catch (Exception e) {
//            return Result.error("系统繁忙，请稍后重试");
//        }
    }


    /**
     * 根据用户ID查询音乐资源
     */
    @Override
    public Result<PageResult> listByUserId(Integer uploadUserId) {
        try {
            List<MusicResource> records = musicResourceMapper.selectByUserId(uploadUserId);
            if (records == null || records.isEmpty()) {
                return Result.success(new PageResult(0L, Collections.emptyList()));
            }
            return Result.success(new PageResult((long) records.size(), records));
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    /**
     * 根据音乐名称查询音乐资源
     */
    @Override
    public Result<PageResult> listByName(String name) {
        try {
            List<MusicResource> records = musicResourceMapper.selectByName(name);
            if (records == null || records.isEmpty()) {
                return Result.success(new PageResult(0L, Collections.emptyList()));
            }
            return Result.success(new PageResult((long) records.size(), records));
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    /**
     * 组合查询音乐资源
     */
    @Override
    public Result<PageResult> listByCondition(Integer uploadUserId, String name) {
        try {
            List<MusicResource> records = musicResourceMapper.selectByCondition(uploadUserId, name);
            if (records == null || records.isEmpty()) {
                return Result.success(new PageResult(0L, Collections.emptyList()));
            }
            return Result.success(new PageResult((long) records.size(), records));
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    /**
     * 用户端分页查询
     * @param musicResourcePageQueryDTO
     * @return
     */
    @Override
    public PageResult userPageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO) {

        Long userId = BaseContext.getCurrentId();
        musicResourcePageQueryDTO.setUserId(userId);

        if (musicResourcePageQueryDTO.getSortBy() == null){
            musicResourcePageQueryDTO.setSortBy("uploadTime");
            musicResourcePageQueryDTO.setSortOrder("DESC");
        }

        PageHelper.startPage(musicResourcePageQueryDTO.getPage(), musicResourcePageQueryDTO.getPageSize());

        Page<MusicResource> pages = musicResourceMapper.userPageQuery(musicResourcePageQueryDTO);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(pages.getTotal());
        pageResult.setRecords(pages.getResult());

        return pageResult;
    }
}
