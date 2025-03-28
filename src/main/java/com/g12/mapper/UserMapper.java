package com.g12.mapper;

import com.g12.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    @Select("select * from music_resource_system.user where user_id = #{id}")
    User get(Long id);

    /**
     * 分页查询
     * @param username
     * @return
     */
    Page<User> pageQuery(String username);


    /**
     * 删除用户
     * @param id
     * @return
     */
    @Delete("DELETE FROM music_resource_system.user WHERE user_id = #{id}")
    int deleteById(Long id);


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return User Information
     */
    @Select("select * FROM music_resource_system.user WHERE user_name = #{username}")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "updateUser", column = "update_user")
    })
    User getByUsername(String username);

    /**
     * 根据id查询用户信息
     * @param id
     * @return User Infomation
     */
    @Select("SELECT * FROM music_resource_system.user WHERE user_id = #{id}")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "updateUser", column = "update_user")
    })
    User getById(Long id);

}
