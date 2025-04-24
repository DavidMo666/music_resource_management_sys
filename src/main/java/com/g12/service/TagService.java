package com.g12.service;


import com.g12.dto.MusicTagDTO;
import com.g12.entity.MusicResource;
import com.g12.entity.Tag;
import com.g12.result.Result;

import java.util.List;

public interface TagService {

    /**
     * 新增tag
     * @param musicTagDTO
     */
    Result addTag(MusicTagDTO[] musicTagDTO);

    /**
     * 用tag筛选获取歌曲
     * @param tagIds
     * @return
     */
    Result<List<MusicResource>> getMusicByTag(Long[] tagIds);

    /**
     * 获取所有tag
     * @return
     */
    Result<List<Tag>> getTags();

}
