package com.g12.unit.controller.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.g12.controller.admin.MusicResourceController;
import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.MusicResourceService;

@ExtendWith(MockitoExtension.class)
public class MusicResourceControllerTest {

    @Mock
    private MusicResourceService musicResourceService;

    @InjectMocks
    private MusicResourceController musicResourceController;

    private PageResult mockPageResult;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockPageResult = new PageResult();
        mockPageResult.setTotal(1L);
        mockPageResult.setRecords(new ArrayList<>());
    }

    @Test
    void pageQuery_ShouldReturnPageResult() {
        // 准备测试数据
        MusicResourcePageQueryDTO queryDTO = new MusicResourcePageQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setPageSize(10);
        when(musicResourceService.adminPageQuery(queryDTO)).thenReturn(mockPageResult);

        // 执行测试
        Result<PageResult> result = musicResourceController.pageQuery(queryDTO);

        // 验证结果
        verify(musicResourceService).adminPageQuery(queryDTO);
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockPageResult, result.getData());
    }

    @Test
    void deleteMusicResource_ShouldDeleteSuccessfully() {
        // 准备测试数据
        String ids = "1,2,3";
        when(musicResourceService.batchDeleteResources(anyList())).thenReturn(3);

        // 执行测试
        Result result = musicResourceController.deleteMusicResource(ids);

        // 验证结果
        verify(musicResourceService).batchDeleteResources(anyList());
        assertNotNull(result);
        assertEquals(1, result.getCode());
    }

    @Test
    void updateStatus_ShouldUpdateSuccessfully() {
        // 准备测试数据
        Integer status = 1;
        Integer id = 1;
        when(musicResourceService.updateStatus(status, id)).thenReturn(true);

        // 执行测试
        Result result = musicResourceController.updateStatus(status, id);

        // 验证结果
        verify(musicResourceService).updateStatus(status, id);
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertTrue(result.getData().equals("音乐资源解封成功"));

    }

    @Test
    void listMusicResource_ShouldReturnList() {
        // 准备测试数据
        Integer uploadUserId = 1;
        String name = "测试音乐";
        Result<PageResult> serviceResult = Result.success(mockPageResult);
        when(musicResourceService.listByCondition(uploadUserId, name)).thenReturn(serviceResult);

        // 执行测试
        Result<PageResult> result = musicResourceController.listMusicResource(uploadUserId, name);

        // 验证结果
        verify(musicResourceService).listByCondition(uploadUserId, name);
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockPageResult, result.getData());
    }

    @Test
    void updateStatus_WithNullId_ShouldReturnError() {
        // 执行测试
        Result result = musicResourceController.updateStatus(1, null);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("参数id不能为空", result.getMsg());
        
        // 验证服务未被调用
        verify(musicResourceService, never()).updateStatus(anyInt(), anyInt());
    }

    @Test
    void updateStatus_WithInvalidStatus_ShouldReturnError() {
        // 执行测试
        Result result = musicResourceController.updateStatus(2, 1);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("状态参数无效，只能为0（封禁）或1（正常）", result.getMsg());
        
        // 验证服务未被调用
        verify(musicResourceService, never()).updateStatus(anyInt(), anyInt());
    }

    @Test
    void updateStatus_WithBanStatus_ShouldReturnBanSuccessMessage() {
        // 准备测试数据
        Integer status = 0; // 封禁状态
        Integer id = 1;
        when(musicResourceService.updateStatus(status, id)).thenReturn(true);
        
        // 执行测试
        Result result = musicResourceController.updateStatus(status, id);
        
        // 验证结果
        verify(musicResourceService).updateStatus(status, id);
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals("音乐资源封禁成功", result.getData());
    }

    @Test
    void updateStatus_WithFailure_ShouldReturnError() {
        // 准备测试数据
        Integer status = 1;
        Integer id = 1;
        when(musicResourceService.updateStatus(status, id)).thenReturn(false);
        
        // 执行测试
        Result result = musicResourceController.updateStatus(status, id);
        
        // 验证结果
        verify(musicResourceService).updateStatus(status, id);
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("音乐资源不存在或状态未变更", result.getMsg());
    }

    @Test
    void updateStatus_WithException_ShouldReturnError() {
        // 准备测试数据
        Integer status = 1;
        Integer id = 1;
        when(musicResourceService.updateStatus(status, id)).thenThrow(new RuntimeException("测试异常"));
        
        // 执行测试
        Result result = musicResourceController.updateStatus(status, id);
        
        // 验证结果
        verify(musicResourceService).updateStatus(status, id);
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("操作失败，请稍后重试", result.getMsg());
    }

    @Test
    void listMusicResource_WithNoParameters_ShouldReturnError() {
        // 执行测试
        Result<PageResult> result = musicResourceController.listMusicResource(null, null);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("至少需要提供一个查询参数(uploadUserId或name)", result.getMsg());
        
        // 验证服务未被调用
        verify(musicResourceService, never()).listByCondition(any(), any());
    }

    @Test
    void listMusicResource_WithEmptyResult_ShouldReturnError() {
        // 准备测试数据
        Integer uploadUserId = 1;
        String name = "测试音乐";
        PageResult emptyPageResult = new PageResult();
        emptyPageResult.setTotal(0L);
        emptyPageResult.setRecords(new ArrayList<>());
        Result<PageResult> serviceResult = Result.success(emptyPageResult);
        when(musicResourceService.listByCondition(uploadUserId, name)).thenReturn(serviceResult);
        
        // 执行测试
        Result<PageResult> result = musicResourceController.listMusicResource(uploadUserId, name);
        
        // 验证结果
        verify(musicResourceService).listByCondition(uploadUserId, name);
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("未找到符合条件的音乐资源", result.getMsg());
    }

    @Test
    void listMusicResource_WithException_ShouldReturnError() {
        // 准备测试数据
        Integer uploadUserId = 1;
        String name = "测试音乐";
        when(musicResourceService.listByCondition(uploadUserId, name)).thenThrow(new RuntimeException("测试异常"));
        
        // 执行测试
        Result<PageResult> result = musicResourceController.listMusicResource(uploadUserId, name);
        
        // 验证结果
        verify(musicResourceService).listByCondition(uploadUserId, name);
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("系统繁忙，请稍后重试", result.getMsg());
    }

    @Test
    void deleteMusicResource_WithEmptyIds_ShouldReturnError() {
        // 执行测试
        Result result = musicResourceController.deleteMusicResource("");
        
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
        Result result = musicResourceController.deleteMusicResource("1,a,3");
        
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
        String ids = "1,2,3";
        when(musicResourceService.batchDeleteResources(anyList())).thenReturn(2); // 只成功删除2条
        
        // 执行测试
        Result result = musicResourceController.deleteMusicResource(ids);
        
        // 验证结果
        verify(musicResourceService).batchDeleteResources(anyList());
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals("成功删除2条数据, 但有1条数据不在数据库中", result.getData());
    }

    @Test
    void deleteMusicResource_WithException_ShouldReturnError() {
        // 准备测试数据
        String ids = "1,2,3";
        when(musicResourceService.batchDeleteResources(anyList())).thenThrow(new RuntimeException("测试异常"));
        
        // 执行测试
        Result result = musicResourceController.deleteMusicResource(ids);
        
        // 验证结果
        verify(musicResourceService).batchDeleteResources(anyList());
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("删除操作失败", result.getMsg());
    }

}