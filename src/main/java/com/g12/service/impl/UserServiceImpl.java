package com.g12.service.impl;

import com.alibaba.fastjson.JSON;
import com.g12.context.BaseContext;
import com.g12.dto.UserDTO;
import com.g12.dto.UserLoginDTO;
import com.g12.dto.UserPageQueryDTO;
import com.g12.dto.UserRegisterDTO;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JwtProperty jwtProperty;

    @Autowired
    JavaMailSender javaMailSender;

    private static final String MAIL_ADDRESS = "2954519433@qq.com";
    private static final String CODE_HEAD = "激活您的账户";

    /**
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

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        if (userMapper.selectById(id) == null) {
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
    public User selectById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户id: " + id + " 不存在");
        }
        return user;
    }


    @Override
    public CaptchaVO getCaptcha() {

        SpecCaptcha captcha = new SpecCaptcha(130, 48, 5);

        //生成验证码
        String code = captcha.text().toLowerCase();
        //生成对应的key
        String verKey = SystemConstants.CAPTCHA_PREFIX + UUID.randomUUID().toString();

        //存redis
        stringRedisTemplate.opsForValue().set( verKey, code, 5L, TimeUnit.MINUTES);

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
        String inputCode = userLoginDTO.getCode().toLowerCase();
        String verKey = userLoginDTO.getVerKey();

        String code = stringRedisTemplate.opsForValue().get(verKey);
        log.info("真实验证码为" + code);
        log.info("输入的为" + inputCode);
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

        if(user.getStatus() == 0){
            return Result.error("用户以封");
        }

        //3.生成Jwt
        Map claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("password", user.getPassword());
        String token = JwtUtil.createJWT(jwtProperty.getUserSecretKey(), jwtProperty.getUserTtl(), claims);

        return Result.success(token);
    }

    /**
     * 注册
     * @param userRegisterDTO
     */
    @Override
    public void register(UserRegisterDTO userRegisterDTO) {

        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);

        //生成验证码
        String code = UUID.randomUUID().toString().substring(0,6);
        String content = "注册验证码为:" + code;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(CODE_HEAD);
        mailMessage.setText(content);
        mailMessage.setFrom(MAIL_ADDRESS);
        mailMessage.setTo(user.getEmail());
        javaMailSender.send(mailMessage);

        //存redis
        String userStr = JSON.toJSONString(user);
        String key = SystemConstants.ACTIVE_CODE_PREFIX + code;
        stringRedisTemplate.opsForValue().set(key, userStr,5, TimeUnit.MINUTES);


    }

    /**
     * 校验激活码
     * @param activeCode
     * @return
     */
    @Override
    public Result registerVerify(String activeCode) {

        //1.从redis获取
        String key = SystemConstants.ACTIVE_CODE_PREFIX + activeCode;
        String userStr = stringRedisTemplate.opsForValue().get(key);

        //2.如果取不到
        if (userStr == null || userStr.length() == 0){
            return Result.error("用户验证码错误");
        }

        //3.取到
        User user = JSON.parseObject(userStr, User.class);
        user.setUserName("new-user" + UUID.randomUUID());
        userMapper.register(user);

        return Result.success();
    }

    /**
     * 获取用户
     * @return
     */
    @Override
    public User getUser() {

        Long userId = BaseContext.getCurrentId();

        User user = userMapper.getUserById(userId);

        return user;
    }

    /**
     * 更新用户信息
     * @param userDTO
     */
    @Override
    public void updateUser(UserDTO userDTO) {

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        Long userId = BaseContext.getCurrentId();
        user.setId(userId);

        userMapper.update(user);
    }


}
