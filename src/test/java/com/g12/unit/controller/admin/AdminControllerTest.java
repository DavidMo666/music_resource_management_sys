package com.g12.unit.controller.admin;

import com.g12.controller.admin.AdminController;
import com.g12.dto.AdminLoginDTO;
import com.g12.result.Result;
import com.g12.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private AdminLoginDTO mockLoginDTO;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockLoginDTO = new AdminLoginDTO();
        mockLoginDTO.setUsername("admin");
        mockLoginDTO.setPassword("password");
    }

    @Test
    void login_ShouldReturnSuccessResult() {
        // 准备测试数据
        String token = "mock-jwt-token";
        when(adminService.login(any(AdminLoginDTO.class))).thenReturn(Result.success(token));

        // 执行测试
        Result<String> result = adminController.login(mockLoginDTO);

        // 验证结果
        verify(adminService).login(mockLoginDTO);
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(token, result.getData());
    }
}