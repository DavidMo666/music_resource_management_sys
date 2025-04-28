package com.g12.mapper;

import com.g12.dto.AdminLoginDTO;
import com.g12.entity.Admin;
import com.g12.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("SELECT id, username, password FROM music_resource_system.admin WHERE username = #{username}")
    Admin login(AdminLoginDTO adminLoginDTO);

    /**
     * 统计用户总数
     */
    @Select("SELECT COUNT(id) FROM music_resource_system.user")
    Long countUsers();

    /**
     * 统计音乐资源总数
     */
    @Select("SELECT COUNT(id) FROM music_resource_system.music_resources")
    Long countMusicResources();
}
