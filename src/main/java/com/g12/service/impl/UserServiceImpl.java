package com.g12.service.impl;

import com.g12.dto.UserLoginDTO;
import com.g12.dto.UserPageQueryDTO;
import com.g12.entity.User;
import com.g12.mapper.UserMapper;
import com.g12.properties.JwtProperty;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.UserService;
import com.g12.util.JwtUtil;
import com.g12.util.SystemConstants;
import com.g12.vo.CaptchaVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JwtProperty jwtProperty;

    /**
     * 更新状态
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {

        User user = new User();
        user.setId(id);
        user.setStatus(status);

        userMapper.update(user);
    }

    /**
     * 分页查询
     * @param userPageQueryDto
     * @return
     */
    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDto) {

        PageHelper.startPage(userPageQueryDto.getPage(), userPageQueryDto.getPageSize());

        Page<User> pages = userMapper.pageQuery(userPageQueryDto.getUserName());

        PageResult pageResult = new PageResult();
        pageResult.setTotal(pages.getTotal());
        pageResult.setRecords(pages.getResult());

        return pageResult;
    }


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

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        if (userMapper.getById(id) == null) {
            throw new RuntimeException("用户: " + id + " 不存在");
        }
        userMapper.deleteById(id);
    }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return User Information
     */
    @Override
    public User getByUsername(String username) {
        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名: " + username + " 不存在");
        }
        return user;
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return User Information
     */
    @Override
    public User getById(Long id) {
        User user = userMapper.getById(id);
        if (user == null) {
            throw new RuntimeException("用户id: " + id + " 不存在");
        }
        return user;
    }

}
