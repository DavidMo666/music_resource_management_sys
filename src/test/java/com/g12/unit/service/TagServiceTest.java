package com.g12.unit.service;

import com.g12.context.BaseContext;
import com.g12.dto.MusicTagDTO;
import com.g12.entity.MusicResource;
import com.g12.entity.MusicTag;
import com.g12.entity.Tag;
import com.g12.mapper.MusicTagMapper;
import com.g12.mapper.TagMapper;
import com.g12.result.Result;
import com.g12.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagMapper tagMapper;

    @Mock
    private MusicTagMapper musicTagMapper;

    @InjectMocks
    private TagServiceImpl tagService;

    private MusicTagDTO mockMusicTagDTO;
    private MusicResource mockMusicResource;
    private List<MusicResource> mockMusicResources;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockMusicTagDTO = new MusicTagDTO();
        mockMusicTagDTO.setMusicId(1L);

        mockMusicResource = new MusicResource();
        mockMusicResource.setId(1L);
        mockMusicResource.setName("测试音乐");
        mockMusicResource.setSinger("测试歌手");
        mockMusicResource.setAlbum("测试专辑");

        mockMusicResources = new ArrayList<>();
        mockMusicResources.add(mockMusicResource);

        // 添加第二个测试音乐资源
        MusicResource secondMusicResource = new MusicResource();
        secondMusicResource.setId(2L);
        secondMusicResource.setName("测试音乐2");
        secondMusicResource.setSinger("测试歌手2");
        secondMusicResource.setAlbum("测试专辑2");
        mockMusicResources.add(secondMusicResource);
    }

    @Test
    void getMusicByTag_ShouldReturnMusicList() {
        // 准备测试数据
        Long[] tagIds = {1L, 2L};
        Long userId = 1L;

        try (MockedStatic<BaseContext> mockedStatic = Mockito.mockStatic(BaseContext.class)) {
            // 模拟 BaseContext.getCurrentId() 返回用户ID
            mockedStatic.when(BaseContext::getCurrentId).thenReturn(userId);

            // 模拟 musicTagMapper.getMusicByTag 方法返回音乐列表
            when(musicTagMapper.getMusicByTag(eq(tagIds), eq(userId))).thenReturn(mockMusicResources);

            // 执行测试
            Result<List<MusicResource>> result = tagService.getMusicByTag(tagIds);

            // 验证结果
            assertEquals(1, result.getCode());
            assertNotNull(result.getData());
            assertEquals(2, result.getData().size());
            assertEquals(mockMusicResources, result.getData());

            // 验证方法调用
            verify(musicTagMapper).getMusicByTag(eq(tagIds), eq(userId));
        }
    }

    @Test
    void getMusicByTag_WithEmptyTagIds_ShouldReturnEmptyList() {
        // 准备测试数据
        Long[] tagIds = {};
        Long userId = 1L;
        List<MusicResource> emptyList = new ArrayList<>();

        try (MockedStatic<BaseContext> mockedStatic = Mockito.mockStatic(BaseContext.class)) {
            // 模拟 BaseContext.getCurrentId() 返回用户ID
            mockedStatic.when(BaseContext::getCurrentId).thenReturn(userId);

            // 模拟 musicTagMapper.getMusicByTag 方法返回空列表
            when(musicTagMapper.getMusicByTag(eq(tagIds), eq(userId))).thenReturn(emptyList);

            // 执行测试
            Result<List<MusicResource>> result = tagService.getMusicByTag(tagIds);

            // 验证结果
            assertEquals(1, result.getCode());
            assertNotNull(result.getData());
            assertTrue(result.getData().isEmpty());

            // 验证方法调用
            verify(musicTagMapper).getMusicByTag(eq(tagIds), eq(userId));
        }
    }
}