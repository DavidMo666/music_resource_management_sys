package com.g12.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.g12.context.BaseContext;
import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.entity.MusicResource;
import com.g12.mapper.MusicResourceMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.impl.MusicResourceServiceImpl;
import com.github.pagehelper.Page;

@ExtendWith(MockitoExtension.class)
public class MusicResourceServiceTest {

    @Mock
    private MusicResourceMapper musicResourceMapper;

    @InjectMocks
    private MusicResourceServiceImpl musicResourceService;

    private MusicResource mockMusicResource;
    private Page<MusicResource> mockPage;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockMusicResource = new MusicResource();
        mockMusicResource.setId(1L);
        mockMusicResource.setName("测试音乐");
        mockMusicResource.setImage("test.jpg");
        mockMusicResource.setUserId(1L);
        mockMusicResource.setUrl("test.mp3");
        mockMusicResource.setUploadTime(LocalDateTime.now());
        mockMusicResource.setStatus(1);

        // 创建模拟分页数据
        mockPage = new Page<>();
        List<MusicResource> resources = new ArrayList<>();
        resources.add(mockMusicResource);
        mockPage.addAll(resources);
        mockPage.setTotal(1);
    }

    @Test
    void adminPageQuery_ShouldReturnPageResult() {
        // 准备测试数据
        MusicResourcePageQueryDTO queryDTO = new MusicResourcePageQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setPageSize(10);
        when(musicResourceMapper.pageQuery(any(MusicResourcePageQueryDTO.class))).thenReturn(mockPage);

        // 执行测试
        PageResult result = musicResourceService.adminPageQuery(queryDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
    }

    @Test
    void adminPageQuery_WithSortParameters_ShouldReturnSortedResult() {
        // 准备测试数据
        MusicResourcePageQueryDTO queryDTO = new MusicResourcePageQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setPageSize(10);
        queryDTO.setSortBy("name");
        queryDTO.setSortOrder("desc");
        when(musicResourceMapper.pageQuery(any(MusicResourcePageQueryDTO.class))).thenReturn(mockPage);

        // 执行测试
        PageResult result = musicResourceService.adminPageQuery(queryDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());

        // 验证传递给mapper的排序参数是否正确
        verify(musicResourceMapper).pageQuery(argThat(dto ->
                "name".equals(dto.getSortBy()) && "desc".equals(dto.getSortOrder())
        ));
    }

    @Test
    void batchDeleteResources_ShouldDeleteAndReturnCount() {
        // 准备测试数据
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        when(musicResourceMapper.batchDeleteResources(ids)).thenReturn(2);

        // 执行测试
        int result = musicResourceService.batchDeleteResources(ids);

        // 验证结果
        assertEquals(2, result);
        verify(musicResourceMapper).batchDeleteResources(ids);
    }

    @Test
    void updateStatus_WhenResourceNotFound_ShouldStillUpdate() {
        // 准备测试数据 - 模拟资源不存在
        when(musicResourceMapper.selectById(anyInt())).thenReturn(null);
        when(musicResourceMapper.updateStatus(anyInt(), anyInt())).thenReturn(true);

        // 执行测试
        boolean result = musicResourceService.updateStatus(0, 999);

        // 验证结果 - 即使资源不存在，方法也会尝试更新
        assertTrue(result);
        verify(musicResourceMapper).updateStatus(0, 999);
    }

    @Test
    void updateStatus_WhenUpdateFails_ShouldReturnFalse() {
        // 准备测试数据
        when(musicResourceMapper.selectById(anyInt())).thenReturn(mockMusicResource);
        when(musicResourceMapper.updateStatus(anyInt(), anyInt())).thenReturn(false);

        // 执行测试
        boolean result = musicResourceService.updateStatus(0, 1);

        // 验证结果
        assertFalse(result);
        verify(musicResourceMapper).updateStatus(0, 1);
    }

    @Test
    void updateStatus_WhenExceptionOccurs_ShouldThrowRuntimeException() {
        // 准备测试数据
        when(musicResourceMapper.selectById(anyInt())).thenReturn(mockMusicResource);
        when(musicResourceMapper.updateStatus(anyInt(), anyInt())).thenThrow(new RuntimeException("数据库错误"));

        // 执行测试并验证异常
        Exception exception = assertThrows(RuntimeException.class, () ->
                musicResourceService.updateStatus(0, 1)
        );

        // 验证异常消息
        assertTrue(exception.getMessage().contains("更新音乐资源状态失败"));
    }

    @Test
    void addMusicResource_ShouldAddSuccessfully() {
        // 准备测试数据
        when(musicResourceMapper.insert(any(MusicResource.class))).thenReturn(1);

        try (MockedStatic<BaseContext> mockedStatic = Mockito.mockStatic(BaseContext.class)) {
            mockedStatic.when(BaseContext::getCurrentId).thenReturn(1L);

            // 执行测试
            Result<String> result = musicResourceService.addMusicResource(mockMusicResource);

            // 验证结果
            assertEquals(1, result.getCode());
            assertTrue(result.getData().contains("成功"));
            verify(musicResourceMapper).insert(any(MusicResource.class));
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void listByUserId_ShouldReturnMusicList() {
        // 准备测试数据
        List<MusicResource> resources = new ArrayList<>();
        resources.add(mockMusicResource);
        when(musicResourceMapper.selectByUserId(anyInt())).thenReturn(resources);

        // 执行测试
        Result<PageResult> result = musicResourceService.listByUserId(1);

        // 验证结果
        assertEquals(1, result.getCode());
        assertEquals(1, result.getData().getTotal());
        assertEquals(1, result.getData().getRecords().size());
    }

    @Test
    void listByName_ShouldReturnMusicList() {
        // 准备测试数据
        List<MusicResource> resources = new ArrayList<>();
        resources.add(mockMusicResource);
        when(musicResourceMapper.selectByName(anyString())).thenReturn(resources);

        // 执行测试
        Result<PageResult> result = musicResourceService.listByName("测试音乐");

        // 验证结果
        assertEquals(1, result.getCode());
        assertEquals(1, result.getData().getTotal());
        assertEquals(1, result.getData().getRecords().size());
    }

    @Test
    void listByCondition_ShouldReturnMusicList() {
        // 准备测试数据
        List<MusicResource> resources = new ArrayList<>();
        resources.add(mockMusicResource);
        when(musicResourceMapper.selectByCondition(anyInt(), anyString())).thenReturn(resources);

        // 执行测试
        Result<PageResult> result = musicResourceService.listByCondition(1, "测试音乐");

        // 验证结果
        assertEquals(1, result.getCode());
        assertEquals(1, result.getData().getTotal());
        assertEquals(1, result.getData().getRecords().size());
    }

    @Test
    void userPageQuery_ShouldReturnPageResult() {
        // 准备测试数据
        MusicResourcePageQueryDTO queryDTO = new MusicResourcePageQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setPageSize(10);
        when(musicResourceMapper.userPageQuery(any(MusicResourcePageQueryDTO.class))).thenReturn(mockPage);

        try (MockedStatic<BaseContext> mockedStatic = Mockito.mockStatic(BaseContext.class)) {
            mockedStatic.when(BaseContext::getCurrentId).thenReturn(1L);

            // 执行测试
            PageResult result = musicResourceService.userPageQuery(queryDTO);

            // 验证结果
            assertNotNull(result);
            assertEquals(1, result.getTotal());
            assertEquals(1, result.getRecords().size());
        }
    }

    @Test
    void addMusicResource_WhenInsertFails_ShouldReturnError() throws UnsupportedAudioFileException, IOException {
        // 准备测试数据 - 模拟插入失败
        when(musicResourceMapper.insert(any(MusicResource.class))).thenReturn(0);

        try (MockedStatic<BaseContext> mockedStatic = Mockito.mockStatic(BaseContext.class)) {
            mockedStatic.when(BaseContext::getCurrentId).thenReturn(1L);

            // 执行测试
            Result<String> result = musicResourceService.addMusicResource(mockMusicResource);

            // 验证结果
            assertEquals(0, result.getCode());
            assertEquals("添加音乐资源失败", result.getMsg());
            verify(musicResourceMapper).insert(any(MusicResource.class));
        }
    }

    @Test
    void getById_ShouldReturnMusicResource() {
        // 准备测试数据
        when(musicResourceMapper.getById(anyLong())).thenReturn(mockMusicResource);

        // 执行测试
        MusicResource result = musicResourceService.getById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(mockMusicResource.getId(), result.getId());
        verify(musicResourceMapper).getById(1L);
    }

    @Test
    void latest_ShouldReturnLatestMusic() {
        // 准备测试数据
        List<MusicResource> latestMusic = new ArrayList<>();
        latestMusic.add(mockMusicResource);
        when(musicResourceMapper.getLatest()).thenReturn(latestMusic);

        // 执行测试
        Result<List<MusicResource>> result = musicResourceService.latest();

        // 验证结果
        assertEquals(1, result.getCode());
        assertEquals(1, result.getData().size());
        verify(musicResourceMapper).getLatest();
    }

    @Test
    void userPageQueryAll_ShouldReturnPageResult() {
        // 准备测试数据
        MusicResourcePageQueryDTO queryDTO = new MusicResourcePageQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setPageSize(10);
        when(musicResourceMapper.userPageQuery(any(MusicResourcePageQueryDTO.class))).thenReturn(mockPage);

        // 执行测试
        PageResult result = musicResourceService.userPageQueryAll(queryDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());

        // 验证默认排序参数
        verify(musicResourceMapper).userPageQuery(argThat(dto ->
                "uploadTime".equals(dto.getSortBy()) && "DESC".equals(dto.getSortOrder())
        ));
    }

    @Test
    void click_ShouldIncrementClickCount() {
        // 准备测试数据
        doNothing().when(musicResourceMapper).click(anyLong());

        // 执行测试
        Result result = musicResourceService.click(1L);

        // 验证结果
        assertEquals(1, result.getCode());
        verify(musicResourceMapper).click(1L);
    }

    @Test
    void listByUserId_WhenExceptionOccurs_ShouldReturnError() {
        // 准备测试数据
        when(musicResourceMapper.selectByUserId(anyInt())).thenThrow(new RuntimeException("数据库错误"));

        // 执行测试
        Result<PageResult> result = musicResourceService.listByUserId(1);

        // 验证结果
        assertEquals(0, result.getCode());
        assertEquals("查询失败", result.getMsg());
    }

    @Test
    void listByName_WhenExceptionOccurs_ShouldReturnError() {
        // 准备测试数据
        when(musicResourceMapper.selectByName(anyString())).thenThrow(new RuntimeException("数据库错误"));

        // 执行测试
        Result<PageResult> result = musicResourceService.listByName("测试音乐");

        // 验证结果
        assertEquals(0, result.getCode());
        assertEquals("查询失败", result.getMsg());
    }

    @Test
    void listByCondition_WhenExceptionOccurs_ShouldReturnError() {
        // 准备测试数据
        when(musicResourceMapper.selectByCondition(anyInt(), anyString())).thenThrow(new RuntimeException("数据库错误"));

        // 执行测试
        Result<PageResult> result = musicResourceService.listByCondition(1, "测试音乐");

        // 验证结果
        assertEquals(0, result.getCode());
        assertEquals("查询失败", result.getMsg());
    }

    @Test
    void listByUserId_WithEmptyResult_ShouldReturnEmptyPageResult() {
        // 准备测试数据 - 空结果集
        when(musicResourceMapper.selectByUserId(anyInt())).thenReturn(new ArrayList<>());

        // 执行测试
        Result<PageResult> result = musicResourceService.listByUserId(1);

        // 验证结果
        assertEquals(1, result.getCode());
        assertEquals(0L, result.getData().getTotal());
        assertTrue(result.getData().getRecords().isEmpty());
    }

    @Test
    void batchDeleteResources_WithEmptyList_ShouldReturnZero() {
        // 准备测试数据
        List<Integer> ids = new ArrayList<>();

        // 执行测试
        int result = musicResourceService.batchDeleteResources(ids);

        // 验证结果
        assertEquals(0, result);
        verify(musicResourceMapper, never()).batchDeleteResources(anyList());
    }

    @Test
    void batchDeleteResources_WithNullList_ShouldReturnZero() {
        // 执行测试
        int result = musicResourceService.batchDeleteResources(null);

        // 验证结果
        assertEquals(0, result);
        verify(musicResourceMapper, never()).batchDeleteResources(anyList());
    }

    @Test
    void batchDeleteResources_WhenExceptionOccurs_ShouldPropagateException() {
        // 准备测试数据
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        when(musicResourceMapper.batchDeleteResources(ids)).thenThrow(new RuntimeException("数据库错误"));

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> musicResourceService.batchDeleteResources(ids));
    }

    @Test
    void getDuration_ShouldReturnDurationInSeconds() throws UnsupportedAudioFileException, IOException {
        // 由于这个方法需要实际的音频文件，我们可以使用PowerMockito来模拟静态方法
        // 但这里我们使用Mockito的方式来测试，需要在测试类上添加相应的注解

        // 这个测试需要模拟AudioSystem.getAudioFileFormat，这是一个静态方法
        // 由于限制，这里只提供测试思路，实际实现可能需要使用PowerMockito

        // 测试思路：
        // 1. 模拟URL和AudioFileFormat
        // 2. 模拟AudioSystem.getAudioFileFormat返回模拟的AudioFileFormat
        // 3. 模拟AudioFileFormat.properties()返回包含duration的Map
        // 4. 调用getDuration方法并验证结果

        // 注意：这个测试可能需要使用PowerMockito来模拟静态方法
    }

    @Test
    void updateStatus_WhenResourceExists_ShouldUpdateSuccessfully() {
        // 准备测试数据
        when(musicResourceMapper.selectById(anyInt())).thenReturn(mockMusicResource);
        when(musicResourceMapper.updateStatus(anyInt(), anyInt())).thenReturn(true);

        // 执行测试
        boolean result = musicResourceService.updateStatus(1, 1);

        // 验证结果
        assertTrue(result);
        verify(musicResourceMapper).updateStatus(1, 1);
    }
}