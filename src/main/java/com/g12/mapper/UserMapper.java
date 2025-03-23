package com.g12.mapper;

import com.g12.entity.User;
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
}
