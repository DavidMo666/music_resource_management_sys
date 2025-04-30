package com.g12.unit.controller.user;

import com.g12.controller.user.UserController;
import com.g12.dto.UserDTO;
import com.g12.dto.UserLoginDTO;
import com.g12.dto.UserRegisterDTO;
import com.g12.entity.User;
import com.g12.result.Result;
import com.g12.service.UserService;
import com.g12.vo.CaptchaVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User mockUser;
    private UserLoginDTO mockLoginDTO;
    private UserRegisterDTO mockRegisterDTO;
    private UserDTO mockUserDTO;
    private CaptchaVO mockCaptchaVO;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUserName("testUser");
        mockUser.setEmail("test@example.com");

        mockLoginDTO = new UserLoginDTO();
        mockLoginDTO.setPassword("password");

        mockRegisterDTO = new UserRegisterDTO();
        mockRegisterDTO.setPassword("password");
        mockRegisterDTO.setEmail("new@example.com");

        mockUserDTO = new UserDTO();
        mockUserDTO.setName("Updated Name");

        mockCaptchaVO = new CaptchaVO("key", "data");
    }

    @Test
    void getCaptcha_ShouldReturnCaptcha() {
        // 准备测试数据
        when(userService.getCaptcha()).thenReturn(mockCaptchaVO);

        // 执行测试
        Result<CaptchaVO> result = userController.getCaptcha();

        // 验证结果
        verify(userService).getCaptcha();
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockCaptchaVO, result.getData());
    }

    @Test
    void login_ShouldLoginSuccessfully() {
        // 准备测试数据
        Result mockResult = Result.success("token");
        when(userService.login(mockLoginDTO)).thenReturn(mockResult);

        // 执行测试
        Result result = userController.login(mockLoginDTO);

        // 验证结果
        verify(userService).login(mockLoginDTO);
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals("token", result.getData());
    }

    @Test
    void register_ShouldRegisterSuccessfully() {
        // 执行测试
        Result result = userController.register(mockRegisterDTO);

        // 验证结果
        verify(userService).register(mockRegisterDTO);
        assertNotNull(result);
        assertEquals(1, result.getCode());
    }

    @Test
    void registerVerify_ShouldVerifySuccessfully() {
        // 准备测试数据
        String activeCode = "123456";
        Result mockResult = Result.success();
        when(userService.registerVerify(activeCode)).thenReturn(mockResult);

        // 执行测试
        Result result = userController.registerVerify(activeCode);

        // 验证结果
        verify(userService).registerVerify(activeCode);
        assertNotNull(result);
        assertEquals(1, result.getCode());
    }

    @Test
    void getUser_ShouldReturnUser() {
        // 准备测试数据
        when(userService.getUser()).thenReturn(mockUser);

        // 执行测试
        Result result = userController.getUser();

        // 验证结果
        verify(userService).getUser();
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockUser, result.getData());
    }

    @Test
    void updateUser_ShouldUpdateSuccessfully() {
        // 执行测试
        Result result = userController.updateUser(mockUserDTO);

        // 验证结果
        verify(userService).updateUser(mockUserDTO);
        assertNotNull(result);
        assertEquals(1, result.getCode());
    }
}