package com.g12.service;

import com.g12.dto.UserPageQueryDto;
import com.g12.entity.User;
import com.g12.result.PageResult;

import java.util.List;

public interface UserService {

    /**
     * 更新用户状态
     *
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 分页查询
     * @param userPageQueryDto
     * @return
     */
    PageResult pageQuery(UserPageQueryDto userPageQueryDto);

    /**
     * 删除用户
     * @param id
     */
    void deleteById(Long id);
    
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return User Information
     */
    User getByUsername(String username);

}
