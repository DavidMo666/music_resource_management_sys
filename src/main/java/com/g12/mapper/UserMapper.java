package com.g12.mapper;

import com.g12.dto.UserLoginDTO;
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
}
