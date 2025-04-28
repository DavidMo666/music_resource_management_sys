package com.g12.mapper;

import com.g12.dto.AdminLoginDTO;
import com.g12.entity.Admin;
import com.g12.entity.User;
import com.g12.vo.TagDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("SELECT \n" +
            "    t.name AS tag_name, \n" +
            "    COUNT(DISTINCT mt.music_id) AS music_count \n" +
            "FROM \n" +
            "    tag t \n" +
            "LEFT JOIN \n" +
            "    music_tag mt ON t.id = mt.tag_id \n" +
            "GROUP BY \n" +
            "    t.id, t.name;")
    List<TagDataVO> countMusicResourcesByTag();
}
