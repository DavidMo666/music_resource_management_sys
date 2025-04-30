package com.g12.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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
import com.g12.service.impl.UserServiceImpl;
import com.g12.util.SystemConstants;
import com.g12.vo.CaptchaVO;
import com.github.pagehelper.Page;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private JwtProperty jwtProperty;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;
    private Page<User> mockPage;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUserName("testUser");
        mockUser.setName("Test Name");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password");
        mockUser.setStatus(1);

        // 创建模拟分页数据
        mockPage = new Page<>();
        List<User> users = new ArrayList<>();
        users.add(mockUser);
        mockPage.addAll(users);
        mockPage.setTotal(1);

        // 使用lenient()来标记这个stubbing，这样即使没有使用也不会报错
        lenient().when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void updateStatus_ShouldUpdateUserStatus() {
        // 执行测试
        userService.updateStatus(0, 1L);

        // 验证结果
        verify(userMapper).update(any(User.class));
    }

    @Test
    void pageQuery_ShouldReturnPageResult() {
        // 准备测试数据
        UserPageQueryDTO queryDTO = new UserPageQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setPageSize(10);
        when(userMapper.pageQuery(any())).thenReturn(mockPage);

        // 执行测试
        PageResult result = userService.pageQuery(queryDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
    }

    @Test
    void deleteById_ShouldDeleteUser() {
        // 准备测试数据
        when(userMapper.selectById(anyLong())).thenReturn(mockUser);

        // 执行测试
        userService.deleteById(1L);

        // 验证结果
        verify(userMapper).deleteById(1L);
    }

    @Test
    void getByUsername_ShouldReturnUser() {
        // 准备测试数据
        when(userMapper.getByUsername(anyString())).thenReturn(mockUser);

        // 执行测试
        User result = userService.getByUsername("testUser");

        // 验证结果
        assertNotNull(result);
        assertEquals("testUser", result.getUserName());
    }

    @Test
    void selectById_ShouldReturnUser() {
        // 准备测试数据
        when(userMapper.selectById(anyLong())).thenReturn(mockUser);

        // 执行测试
        User result = userService.selectById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void getCaptcha_ShouldReturnCaptchaVO() {
        // 执行测试
        CaptchaVO result = userService.getCaptcha();

        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getVerKey());
        verify(valueOperations).set(anyString(), anyString(), eq(5L), eq(TimeUnit.MINUTES));
    }

    @Test
    void login_ShouldReturnSuccessResult() {
        // 准备测试数据
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setPassword("password");
        loginDTO.setCode("code");
        loginDTO.setVerKey("verKey");

        when(valueOperations.get(anyString())).thenReturn("code");
        when(userMapper.login(any(UserLoginDTO.class))).thenReturn(mockUser);
        when(jwtProperty.getUserSecretKey()).thenReturn("testSecretKey");
        when(jwtProperty.getUserTtl()).thenReturn(3600000L);

        // 执行测试
        Result result = userService.login(loginDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        verify(stringRedisTemplate).delete("verKey");
    }

    @Test
    void register_ShouldRegisterUser() {
        // 准备测试数据
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setPassword("password");
        registerDTO.setEmail("new@example.com");

        // 执行测试
        userService.register(registerDTO);

        // 验证结果
        verify(javaMailSender).send(any(SimpleMailMessage.class));
        verify(valueOperations).set(anyString(), anyString(), eq(5L), eq(TimeUnit.MINUTES));
    }

    @Test
    void registerVerify_ShouldVerifyAndReturnSuccess() {
        // 准备测试数据
        String activeCode = "123456";
        String userJson = JSON.toJSONString(mockUser);
        when(valueOperations.get(SystemConstants.ACTIVE_CODE_PREFIX + activeCode)).thenReturn(userJson);

        // 执行测试
        Result result = userService.registerVerify(activeCode);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        verify(userMapper).register(any(User.class));
    }

    @Test
    void getUser_ShouldReturnCurrentUser() {
        // 使用MockedStatic模拟静态方法BaseContext.getCurrentId()
        try (MockedStatic<BaseContext> mockedStatic = Mockito.mockStatic(BaseContext.class)) {
            // 设置模拟返回值
            mockedStatic.when(BaseContext::getCurrentId).thenReturn(1L);

            // 模拟userMapper.getUserById返回用户
            when(userMapper.getUserById(1L)).thenReturn(mockUser);

            // 执行测试
            User result = userService.getUser();

            // 验证结果
            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("testUser", result.getUserName());

            // 验证方法调用
            verify(userMapper).getUserById(1L);
        }
    }

    @Test
    void updateUser_ShouldUpdateCurrentUser() {
        // 准备测试数据
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Updated Name");

        // 使用MockedStatic模拟静态方法BaseContext.getCurrentId()
        try (MockedStatic<BaseContext> mockedStatic = Mockito.mockStatic(BaseContext.class)) {
            // 设置模拟返回值
            mockedStatic.when(BaseContext::getCurrentId).thenReturn(1L);

            // 执行测试
            userService.updateUser(userDTO);

            // 验证方法调用
            verify(userMapper).update(any(User.class));
        }
    }@Test
    void login_WithInvalidCaptcha_ShouldReturnError() {
        // 准备测试数据
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setPassword("password");
        loginDTO.setCode("wrongCode");
        loginDTO.setVerKey("verKey");

        when(valueOperations.get(anyString())).thenReturn("correctCode");

        // 执行测试
        Result result = userService.login(loginDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("验证码输入错误", result.getMsg());
        verify(stringRedisTemplate, never()).delete(anyString());
    }

    @Test
    void login_WithNullCaptcha_ShouldReturnError() {
        // 准备测试数据
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setPassword("password");
        loginDTO.setCode("code");
        loginDTO.setVerKey("verKey");

        when(valueOperations.get(anyString())).thenReturn(null);

        // 执行测试
        Result result = userService.login(loginDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("验证码输入错误", result.getMsg());
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnError() {
        // 准备测试数据
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setPassword("wrongPassword");
        loginDTO.setCode("code");
        loginDTO.setVerKey("verKey");

        when(valueOperations.get(anyString())).thenReturn("code");
        when(userMapper.login(any(UserLoginDTO.class))).thenReturn(null);

        // 执行测试
        Result result = userService.login(loginDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("用户名或密码输入错误", result.getMsg());
        verify(stringRedisTemplate).delete("verKey");
    }

    @Test
    void login_WithBlockedUser_ShouldReturnError() {
        // 准备测试数据
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setPassword("password");
        loginDTO.setCode("code");
        loginDTO.setVerKey("verKey");

        // 创建一个被封禁的用户
        User blockedUser = new User();
        blockedUser.setId(1L);
        blockedUser.setUserName("blockedUser");
        blockedUser.setStatus(0); // 封禁状态

        when(valueOperations.get(anyString())).thenReturn("code");
        when(userMapper.login(any(UserLoginDTO.class))).thenReturn(blockedUser);

        // 执行测试
        Result result = userService.login(loginDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("用户以封", result.getMsg());
        verify(stringRedisTemplate).delete("verKey");
    }
}

