package com.g12.mapper;

import com.g12.entity.MusicFavourite;
import com.g12.entity.MusicResource;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.DeleteMapping;

@Mapper
public interface FavouriteMapper {

    @Insert("insert into favourites(music_id, user_id, create_time) " +
            "values (#{musicId}, #{userId}, #{createTime})")
    void add(MusicFavourite favourite);

    @Delete("delete from favourites where music_id = #{musicId} and user_id = #{userId}")
    void delete(MusicFavourite favourite);

    Page<MusicResource> get(Long userId);
}
