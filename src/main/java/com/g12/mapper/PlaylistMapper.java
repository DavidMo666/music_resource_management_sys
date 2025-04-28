package com.g12.mapper;

import com.g12.entity.MusicResource;
import com.g12.entity.PlaylistItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface PlaylistMapper {

    List<MusicResource> getMusic(Long userId);

    @Insert("insert into playlist(music_id, user_id, create_time) " +
            "values (#{musicId},#{userId},#{createTime})")
    void add(PlaylistItem p);

    @Delete("delete from playlist where music_id = #{musicId} and user_id = #{userId}")
    void delete(PlaylistItem p);
}
