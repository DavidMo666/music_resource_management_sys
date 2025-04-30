package com.g12.unit.controller.admin;

import com.g12.controller.admin.UserController;
import com.g12.dto.UserPageQueryDTO;
import com.g12.entity.User;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User mockUser;
    private PageResult mockPageResult;

    @BeforeEach
    void setUp() {
        // 准备测试用户数据
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUserName("testUser");
        mockUser.setName("Test Name");
        mockUser.setEmail("test@example.com");
        mockUser.setPhone("13800138000");
        mockUser.setStatus(1);
        mockUser.setCreateTime(LocalDateTime.now());

        // 准备分页数据
        mockPageResult = new PageResult();
        mockPageResult.setTotal(1L);
        mockPageResult.setRecords(java.util.Collections.singletonList(mockUser));
    }

    // 1. 正常功能测试
    @Test
    void updateStatus_ShouldUpdateSuccessfully() {
        // 准备测试数据
        Integer status = 1;
        Long id = 1L;

        // 执行测试
        Result result = userController.updateStatus(status, id);

        // 验证结果
        verify(userService).updateStatus(status, id);
        assertEquals(Result.success().getCode(), result.getCode());
    }

    @Test
    void pageQuery_ShouldReturnPageResult() {
        // 准备测试数据
        UserPageQueryDTO queryDTO = new UserPageQueryDTO();
        when(userService.pageQuery(queryDTO)).thenReturn(mockPageResult);

        // 执行测试
        Result result = userController.pageQuery(queryDTO);

        // 验证结果
        verify(userService).pageQuery(queryDTO);
        assertEquals(Result.success(mockPageResult).getCode(), result.getCode());
        assertEquals(mockPageResult, result.getData());
    }

    @Test
    void deleteById_ShouldDeleteSuccessfully() {
        // 准备测试数据
        Long id = 1L;

        // 执行测试
        Result result = userController.deleteById(id);

        // 验证结果
        verify(userService).deleteById(id);
        assertEquals(Result.success().getCode(), result.getCode());
    }

    @Test
    void deleteById_ShouldReturnSuccess() {
        // 准备测试数据
        Long userId = 1L;

        // 执行测试
        Result result = userController.deleteById(userId);

        // 验证结果
        verify(userService).deleteById(userId);
        assertNotNull(result);
        assertEquals(1, result.getCode());
    }

    @Test
    void getByUsername_ShouldReturnUser() {
        // 准备测试数据
        String username = "testUser";
        when(userService.getByUsername(username)).thenReturn(mockUser);

        // 执行测试
        Result<User> result = userController.getByUsername(username);

        // 验证结果
        verify(userService).getByUsername(username);
        assertEquals(Result.success(mockUser).getCode(), result.getCode());
        assertEquals(mockUser, result.getData());
    }

    @Test
    void getById_ShouldReturnUser() {
        // 准备测试数据
        Long id = 1L;
        when(userService.selectById(id)).thenReturn(mockUser);

        // 执行测试
        Result<User> result = userController.getById(id);

        // 验证结果
        verify(userService).selectById(id);
        assertEquals(Result.success(mockUser).getCode(), result.getCode());
        assertEquals(mockUser, result.getData());
    }

    @Test
    void getByUsername_ShouldHandleUserNotFound() {
        // 准备测试数据
        String username = "nonexistent";
        when(userService.getByUsername(username)).thenReturn(null);

        // 执行测试
        Result<User> result = userController.getByUsername(username);

        // 验证结果
        verify(userService).getByUsername(username);
        assertNull(result.getData());
    }
}