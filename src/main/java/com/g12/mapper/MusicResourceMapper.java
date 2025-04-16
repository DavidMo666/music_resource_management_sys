package com.g12.mapper;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

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
     * 更新音乐资源状态
     * @param status 状态（0-封禁，1-正常）
     * @param id 音乐资源ID
     * @return 是否更新成功
     */
    boolean updateStatus(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 根据音乐ID查询音乐资源
     * @return 音乐资源
     */
    MusicResource selectById(Integer Id);

    /**
     * 新增音乐资源
     * @param musicResource 音乐资源信息
     * @return 操作结果
     */
    int insert(MusicResource musicResource);

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
     * 根据条件查询音乐资源（支持按用户ID、名称或组合查询）
     * @param uploadUserId 上传用户ID（可选）
     * @param name 音乐名称（可选，支持模糊查询）
     * @return 音乐资源列表
     */
    List<MusicResource> selectByCondition(@Param("uploadUserId") Integer uploadUserId, @Param("name") String name);
}
