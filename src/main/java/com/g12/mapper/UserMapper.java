package com.g12.mapper;

import com.g12.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

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
    int deleteById(Long id);

    /**
     * 查询用户
     * @param id
     * @return
     */
    User selectById(Long id);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return User Information
     */
    @Select("select * FROM music_resource_system.user WHERE user_name = #{username}")
    User getByUsername(String username);

}
