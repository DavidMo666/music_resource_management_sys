package com.g12.service.impl;

import com.g12.dto.UserLoginDTO;
import com.g12.entity.User;
import com.g12.mapper.UserMapper;
import com.g12.properties.JwtProperty;
import com.g12.result.Result;
import com.g12.service.LoginService;
import com.g12.util.JwtUtil;
import com.g12.util.SystemConstants;
import com.g12.vo.CaptchaVO;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JwtProperty jwtProperty;
    
    @Override
    public CaptchaVO getCaptcha() {

        SpecCaptcha captcha = new SpecCaptcha(130, 48, 5);

        //生成验证码
        String code = captcha.text().toLowerCase();
        //生成对应的key
        String verKey = UUID.randomUUID().toString();

        //存redis
        stringRedisTemplate.opsForValue().set(SystemConstants.CAPTCHA_PREFIX + verKey, code, 5L, TimeUnit.MINUTES);

        CaptchaVO captchaVo = new CaptchaVO(verKey, captcha.toBase64());

        return captchaVo;
    }

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public Result login(UserLoginDTO userLoginDTO) {

        //1.检查验证码是否正确
        String inputCode = userLoginDTO.getCode();
        String verKey = userLoginDTO.getVerKey();

        String code = stringRedisTemplate.opsForValue().get(verKey);
        if (code == null){
            return Result.error("验证码输入错误");
        }

        code = code.toLowerCase();
        if (!inputCode.equals(code)){
            return Result.error("验证码输入错误");
        }

        stringRedisTemplate.delete(verKey);

        //2.检查用户名密码
        User user = userMapper.login(userLoginDTO);

        if (user == null){
            return Result.error("用户名或密码输入错误");
        }

        //3.生成Jwt
        Map claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("password", user.getPassword());
        String token = JwtUtil.createJWT(jwtProperty.getUserSecretKey(), jwtProperty.getUserTtl(), claims);

        return Result.success(token);
    }
}
