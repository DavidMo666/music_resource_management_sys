package com.g12.service;

import com.g12.entity.MusicResource;
import com.g12.result.Result;

import java.util.List;

public interface PlaylistService {

    /**
     * 获取播放列表里音乐
     * @return
     */
    Result<List<MusicResource>> getMusicInPlayList();

    /**
     * 增加
     * @param musicId
     * @return
     */
    Result addMusic(Long musicId);

    /**
     * 删除
     * @param musicId
     * @return
     */
    Result deleteMusic(Long musicId);
}
