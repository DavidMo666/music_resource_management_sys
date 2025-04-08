package com.g12.service;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.result.PageResult;

import java.util.List;

public interface MusicResourceService {

    /**
     * 音乐资源分页查询
     * @param musicResourcePageQueryDTO
     * @return
     */
    PageResult pageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO);

    int batchDeleteResources(List<Integer> ids);
    
    /**
     * 根据用户ID查询音乐资源
     * @param userId 用户ID
     * @return 音乐资源列表
     */
    PageResult listByUserId(Integer userId);

    /**
     * 根据音乐名称查询音乐资源
     * @param name 音乐名称
     * @return 分页结果
     */
    PageResult listByName(String name);

     /**
     * 组合查询音乐资源
     * @param userId 用户ID
     * @param name 音乐名称
     * @return 分页结果
     */
    PageResult listByCondition(Integer userId, String name);
}
