package com.g12.service.impl;

import com.g12.service.LoginService;
import com.g12.util.SystemConstants;
import com.g12.vo.CaptchaVO;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    @Override
    public CaptchaVO getCaptcha() {

        SpecCaptcha captcha = new SpecCaptcha(130, 48, 5);

        //生成验证码
        String code = captcha.text().toLowerCase();
        //生成对应的key
        String key = UUID.randomUUID().toString();

        //存redis
        stringRedisTemplate.opsForValue().set(SystemConstants.CAPTCHA_PREFIX + key, code, 5L, TimeUnit.MINUTES);

        CaptchaVO captchaVo = new CaptchaVO(key, captcha.toBase64());

        return captchaVo;
    }
}
