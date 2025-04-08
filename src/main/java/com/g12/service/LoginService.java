package com.g12.service;

import com.g12.dto.UserLoginDTO;
import com.g12.result.Result;
import com.g12.vo.CaptchaVO;

public interface LoginService {

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
