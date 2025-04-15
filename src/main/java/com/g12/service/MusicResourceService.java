package com.g12.service;


import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.g12.result.PageResult;
import com.g12.result.Result;

import java.util.List;

public interface MusicResourceService {

    /**
     * 音乐资源分页查询
     * @param musicResourcePageQueryDTO
     * @return
     */
    PageResult pageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO);

    /**
     * 批量删除音乐资源
     * @param ids
     * @return
     */
    int batchDeleteResources(List<Integer> ids);

    /**
     * 更新音乐资源状态
     * @param status 状态（0-封禁，1-正常）
     * @param id 音乐资源ID
     * @return 是否更新成功
     */
    boolean updateStatus(Integer status, Integer id);

    /**
     * 新增音乐资源
     * @param musicResource 音乐资源信息
     * @return 操作结果
     */
    Result<String> addMusicResource(MusicResource musicResource);

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
