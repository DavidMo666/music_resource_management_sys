package com.g12.service.impl;

import com.g12.context.BaseContext;
import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.dto.UpdateMusicResourceDTO;
import com.g12.entity.MusicResource;
import com.g12.entity.MusicTag;
import com.g12.mapper.MusicResourceMapper;
import com.g12.mapper.MusicTagMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.MusicResourceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MusicResourceServiceImpl implements MusicResourceService {

    @Autowired
    MusicResourceMapper musicResourceMapper;

    @Autowired
    MusicTagMapper musicTagMapper;

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
    public Result<String> addMusicResource(MusicResource musicResource) throws UnsupportedAudioFileException, IOException {


        // 设置默认值
        Long userId = BaseContext.getCurrentId();

        musicResource.setUserId(userId);
        musicResource.setUploadTime(LocalDateTime.now());
        musicResource.setStatus(1);

        //音乐时长
//        int duration = getDuration(musicResource.getUrl());
//        musicResource.setDuration(duration);

        // 调用Mapper插入数据
        int result = musicResourceMapper.insert(musicResource);
        if (result <= 0) {
            return Result.error("添加音乐资源失败");
        }

        return Result.success("音乐资源添加成功");
    }


    public int getDuration(String path) throws UnsupportedAudioFileException, IOException {

        URL url = new URL(path);

        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(url);

        Map<?, ?> properties = fileFormat.properties();
        Long durationMicroseconds = (Long) properties.get("duration");

        int durationInSeconds = (int) (durationMicroseconds / 1_000_000);
        System.out.println("Duration: " + durationInSeconds + " seconds");

        return durationInSeconds;
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

    /**
     * 音乐资源更新
     * @param
     */
    @Transactional
    @Override
    public void updateMusicResource(UpdateMusicResourceDTO updateMusicResourceDTO) {

        MusicResource musicResource = new MusicResource();
        BeanUtils.copyProperties(updateMusicResourceDTO,musicResource);

        Long userId = BaseContext.getCurrentId();
        musicResource.setUploadTime(LocalDateTime.now());
        musicResource.setUserId(userId);
        musicResourceMapper.updateMusicResource(musicResource);

        //tag
        //1.删除此用户此音乐的所有tag
        MusicTag musicTag = new MusicTag();
        musicTag.setUserId(userId);
        musicTag.setMusicId(musicResource.getId());
        musicTagMapper.delete(musicTag);

        //2.新增tag
        List<MusicTag> tagList = new ArrayList<>();
        Long[] tagIds = updateMusicResourceDTO.getTagIds();
        if(tagIds != null && tagIds.length != 0){
            for (Long tagId : tagIds) {
                MusicTag tag = new MusicTag();
                tag.setMusicId(musicResource.getId());
                tag.setUserId(userId);
                tag.setTagId(tagId);
                tag.setCreateTime(LocalDateTime.now());

                tagList.add(tag);
            }

            musicTagMapper.insertBatch(tagList);
        }


    }

    /**
     * 根据id获取音乐
     * @param id
     * @return
     */
    @Override
    public MusicResource getById(Long id) {

        MusicResource mr = musicResourceMapper.getById(id);

        return mr;
    }

    /**
     * 最新
     * @return
     */
    @Override
    public Result<List<MusicResource>> latest() {
        List<MusicResource> list = musicResourceMapper.getLatest();
        return Result.success(list);
    }

    /**
     * 全部
     * @param musicResourcePageQueryDTO
     * @return
     */
    @Override
    public PageResult userPageQueryAll(MusicResourcePageQueryDTO musicResourcePageQueryDTO) {
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

    /**
     * 点击
     * @param musicId
     * @return
     */
    @Override
    public Result click(Long musicId) {
        musicResourceMapper.click(musicId);
        return Result.success();
    }
}
