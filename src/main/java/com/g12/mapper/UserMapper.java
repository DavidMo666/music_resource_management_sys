package com.g12.mapper;

import com.g12.dto.UserLoginDTO;
import org.apache.ibatis.annotations.*;

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
    User get(Long id);

    /**
     * 分页查询
     * @param username
     * @return
     */
    Page<User> pageQuery(String username);

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Select("select * from user where email = #{email} and password = #{password}")
    User login(UserLoginDTO userLoginDTO);


    /**
     * 删除用户
     * @param id
     * @return
     */
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
     * @return User Information
     */
    @Select("select * FROM music_resource_system.user WHERE user_id = #{id}")
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
