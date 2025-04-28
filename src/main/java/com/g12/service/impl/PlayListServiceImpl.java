package com.g12.service.impl;

import com.g12.context.BaseContext;
import com.g12.entity.MusicResource;
import com.g12.entity.PlaylistItem;
import com.g12.mapper.PlaylistMapper;
import com.g12.result.Result;
import com.g12.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlayListServiceImpl implements PlaylistService {

    @Autowired
    PlaylistMapper playlistMapper;

    /**
     * 获取播放列表里音乐
     * @return
     */
    @Override
    public Result<List<MusicResource>> getMusicInPlayList() {
        Long userId = BaseContext.getCurrentId();
        List<MusicResource> list = playlistMapper.getMusic(userId);
        return Result.success(list);
    }

    /**
     * 增加
     * @param musicId
     * @return
     */
    @Transactional
    @Override
    public Result addMusic(Long musicId) {
        Long userId = BaseContext.getCurrentId();
        PlaylistItem p = new PlaylistItem();
        p.setMusicId(musicId);
        p.setUserId(userId);
        p.setCreateTime(LocalDateTime.now());

        //删除之前
        playlistMapper.delete(p);

        playlistMapper.add(p);

        return Result.success();
    }

    /**
     * 删
     * @param musicId
     * @return
     */
    @Override
    public Result deleteMusic(Long musicId) {
        Long userId = BaseContext.getCurrentId();
        PlaylistItem p = new PlaylistItem();
        p.setMusicId(musicId);
        p.setUserId(userId);

        playlistMapper.delete(p);

        return Result.success();
    }
}
