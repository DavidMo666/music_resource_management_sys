package com.g12.mapper;

import com.g12.entity.MusicResource;
import com.g12.entity.MusicTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MusicTagMapper {

    /**
     * 增加tag
     * @param musicTag
     */
    void addTag(MusicTag musicTag);

    /**
     * 根据tag获取音乐资源
     * @param tagName
     * @param userId
     * @return
     */
    List<MusicResource> getMusicByTag(String tagName, Long userId);
}
