package com.g12.mapper;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicResourceMapper {

    /**
     * 音乐资源分页查询
     * @param musicResourcePageQueryDTO
     * @return
     */
    Page<MusicResource> pageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO);

    /**
     * 批量删除音乐资源
     * @param ids 资源 ID 列表
     * @return 删除的记录数
     */
    int batchDeleteResources(List<Integer> ids);

    /**
     * 根据用户ID查询音乐资源
     * @param userId 用户ID
     * @return 音乐资源列表
     */
    List<MusicResource> selectByUserId(Integer userId);

    /**
     * 根据音乐名称查询音乐资源
     * @param name 音乐名称
     * @return 音乐资源列表
     */
    List<MusicResource> selectByName(String name);

    /**
     * 根据用户ID和音乐名称组合查询
     * @param userId 用户ID
     * @param name 音乐名称
     * @return 音乐资源列表
     */
    List<MusicResource> selectByCondition(@Param("userId") Integer userId, @Param("name") String name);
}
