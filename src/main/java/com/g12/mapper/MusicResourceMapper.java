package com.g12.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.g12.entity.MusicResource;

import java.util.List;

@Mapper
public interface MusicResourceMapper {

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
    @Select("SELECT * FROM music_resource WHERE upload_user_id = #{userId}")
    List<MusicResource> selectByUserId(Integer userId);

    /**
     * 根据音乐名称查询音乐资源
     * @param name 音乐名称
     * @return 音乐资源列表
     */
    @Select("SELECT * FROM music_resource WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<MusicResource> selectByName(String name);

    /**
     * 根据用户ID和音乐名称组合查询
     * @param userId 用户ID
     * @param name 音乐名称
     * @return 音乐资源列表
     */
    @Select("<script>" +
            "SELECT * FROM music_resource WHERE 1=1 " +
            "<if test='userId != null'> AND upload_user_id = #{userId} </if>" +
            "<if test='name != null'> AND name LIKE CONCAT('%', #{name}, '%') </if>" +
            "</script>")
    List<MusicResource> selectByCondition(@Param("userId") Integer userId, @Param("name") String name);
}
