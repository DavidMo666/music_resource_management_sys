package com.g12.mapper;

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
     * 删除用户
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据用户名查询用户信息
    * @param username 用户名
    * @return 用户信息
    */
    User getByUsername(String username);

    /**
     * 根据id查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    User getById(Long id);
}
