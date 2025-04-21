package com.g12.service.impl;

import com.g12.context.BaseContext;
import com.g12.dto.MusicTagDTO;
import com.g12.entity.MusicResource;
import com.g12.entity.MusicTag;
import com.g12.entity.Tag;
import com.g12.mapper.MusicTagMapper;
import com.g12.mapper.TagMapper;
import com.g12.result.Result;
import com.g12.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    MusicTagMapper musicTagMapper;

    @Override
    public Result addTag(MusicTagDTO musicTagDTO) {

        //1.查询tag 在tag表中是否存在
        String tagName = musicTagDTO.getName();

        Long tagId = tagMapper.get(tagName);

        //1.1不在 加入
        if (tagId == null || tagId == 0){
            Tag tag = new Tag();
            tag.setCreateTime(LocalDateTime.now());
            tag.setName(tagName);

            tagId = tagMapper.addTag(tag);
        }

        //2.在 在music_tag关系表中添加
        Long userId = BaseContext.getCurrentId();
        MusicTag musicTag = new MusicTag();
        musicTag.setCreateTime(LocalDateTime.now());
        musicTag.setMusicId(musicTagDTO.getMusicId());
        musicTag.setTagId(tagId);
        musicTag.setUserId(userId);

        musicTagMapper.addTag(musicTag);

        return Result.success();
    }

    /**
     * 用tag筛选获取歌曲
     * @param tagName
     * @return
     */
    @Override
    public Result<List<MusicResource>> getMusicByTag(String tagName) {

        //
        Long userId = BaseContext.getCurrentId();

        //根据userId tagId 连表查询
        List<MusicResource> musicResourceList = musicTagMapper.getMusicByTag(tagName, userId);

        return Result.success(musicResourceList);
    }
}