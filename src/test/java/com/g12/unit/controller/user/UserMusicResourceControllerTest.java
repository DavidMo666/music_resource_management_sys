package com.g12.unit.controller.user;

import com.g12.controller.user.UserMusicResourceController;
import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.MusicResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMusicResourceControllerTest {

    @Mock
    private MusicResourceService musicResourceService;

    @InjectMocks
    private UserMusicResourceController userMusicResourceController;

    private MusicResource mockMusicResource;

    @BeforeEach
    void setUp() {
        mockMusicResource = new MusicResource();
        mockMusicResource.setId(1L);
        mockMusicResource.setName("测试音乐");
        mockMusicResource.setImage("test.jpg");
        mockMusicResource.setUserId(1L);
        mockMusicResource.setUrl("test.mp3");
        mockMusicResource.setUploadTime(LocalDateTime.now());
        mockMusicResource.setStatus(1);
    }

    @Test
    void deleteMusicResource_ShouldDeleteSuccessfully() {
        // 准备测试数据
        List<Integer> ids = new ArrayList<>(Arrays.asList(1,2,3));
        when(musicResourceService.batchDeleteResources(anyList())).thenReturn(3);

        // 执行测试
        Result result = userMusicResourceController.deleteMusicResource(ids);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertTrue(result.getData().equals("成功删除3条数据"));
    }

    @Test
    void pageQuery_ShouldReturnPageResult() {
        // 准备测试数据
        MusicResourcePageQueryDTO queryDTO = new MusicResourcePageQueryDTO();
        PageResult mockPageResult = new PageResult();
        List<MusicResource> records = new ArrayList<>();
        records.add(mockMusicResource);
        mockPageResult.setRecords(records);
        mockPageResult.setTotal(1L);

        when(musicResourceService.userPageQuery(queryDTO)).thenReturn(mockPageResult);

        // 执行测试
        Result result = userMusicResourceController.pageQuery(queryDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockPageResult, result.getData());
    }

    @Test
    void deleteMusicResource_WithEmptyIds_ShouldReturnError() {
        // 执行测试
        Result result = userMusicResourceController.deleteMusicResource(new ArrayList<>());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("参数ids不能为空", result.getMsg());
        
        // 验证服务未被调用
        verify(musicResourceService, never()).batchDeleteResources(anyList());
    }

    @Test
    void deleteMusicResource_WithInvalidIds_ShouldReturnError() {
        // 执行测试
        Result result = userMusicResourceController.deleteMusicResource(Arrays.asList(1));
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("参数包含非法字符", result.getMsg());
        
        // 验证服务未被调用
        verify(musicResourceService, never()).batchDeleteResources(anyList());
    }

    @Test
    void deleteMusicResource_WithPartialSuccess_ShouldReturnPartialSuccessMessage() {
        // 准备测试数据
        List<Integer> ids = new ArrayList<>(Arrays.asList(1,2,3));
        when(musicResourceService.batchDeleteResources(anyList())).thenReturn(2); // 只成功删除2条
        
        // 执行测试
        Result result = userMusicResourceController.deleteMusicResource(ids);
        
        // 验证结果
        verify(musicResourceService).batchDeleteResources(anyList());
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals("成功删除2条数据, 但有1条数据不在数据库中", result.getData());
    }

    @Test
    void deleteMusicResource_WithException_ShouldReturnError() {
        // 准备测试数据
        List<Integer> ids = new ArrayList<>(Arrays.asList(1,2,3));
        when(musicResourceService.batchDeleteResources(anyList())).thenThrow(new RuntimeException("测试异常"));
        
        // 执行测试
        Result result = userMusicResourceController.deleteMusicResource(ids);
        
        // 验证结果
        verify(musicResourceService).batchDeleteResources(anyList());
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("删除操作失败", result.getMsg());
    }
}