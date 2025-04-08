package com.g12.service;

import com.g12.vo.CaptchaVO;

public interface LoginService {

    /**
     * 创建验证码
     * @return
     */
    CaptchaVO getCaptcha();

}
