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

    /**
     * 给音乐增加tag
     * @param musicTagDTOs
     * @return
     */
    @Override
    public Result addTag(MusicTagDTO[] musicTagDTOs) {

        return Result.success();
    }

    /**
     * 用tag筛选获取歌曲
     * @param tagIds
     * @return
     */
    @Override
    public Result<List<MusicResource>> getMusicByTag(Long[] tagIds) {
        //
        Long userId = BaseContext.getCurrentId();

        //根据userId tagId 连表查询
        List<MusicResource> musicResourceList = musicTagMapper.getMusicByTag(tagIds, userId);

        return Result.success(musicResourceList);
    }

    /**
     * 获取所有tag
     * @return
     */
    @Override
    public Result<List<Tag>> getTags() {

        List<Tag> tagList = tagMapper.getTags();

        return Result.success(tagList);
    }
}