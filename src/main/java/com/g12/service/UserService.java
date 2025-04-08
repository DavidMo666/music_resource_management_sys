package com.g12.service;

import com.g12.dto.UserLoginDTO;
import com.g12.dto.UserPageQueryDTO;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.vo.CaptchaVO;

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
