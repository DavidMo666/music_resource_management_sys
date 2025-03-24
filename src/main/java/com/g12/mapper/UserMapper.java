package com.g12.mapper;

import com.g12.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface UserMapper {


    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    @Select("select * from music_resource_system.user where id = #{id}")
    User get(Long id);

    /**
     * 分页查询
     * @param username
     * @return
     */
    Page<User> pageQuery(String username);
}
