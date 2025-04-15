package com.g12.service;

import com.g12.dto.UserLoginDTO;
import com.g12.dto.UserPageQueryDTO;
import com.g12.entity.User;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.vo.CaptchaVO;

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
    PageResult pageQuery(UserPageQueryDTO userPageQueryDto);

    /**
     * 根据id查询用户信息
     * @param id
     * @return User Information
     */
    User selectById(Long id);

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


    /**
     * 创建验证码
     * @return
     */
    CaptchaVO getCaptcha();

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    Result login(UserLoginDTO userLoginDTO);
}
