package com.g12.service;

import com.g12.entity.MusicResource;
import com.g12.result.PageResult;
import com.g12.result.Result;

import java.util.List;

public interface FavouriteService {

    /**
     * 增
     * @param musicId
     * @return
     */
    Result add(Long musicId);

    /**
     * 移除
     * @param musicId
     * @return
     */
    Result remove(Long musicId);

    /**
     * 查询
     * @return
     */
    Result<PageResult> get(int page, int pageSize);

}
