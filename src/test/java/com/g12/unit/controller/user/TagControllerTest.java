package com.g12.unit.controller.user;

import com.g12.controller.user.TagController;
import com.g12.entity.MusicResource;
import com.g12.entity.Tag;
import com.g12.result.Result;
import com.g12.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TagControllerTest {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    private List<MusicResource> mockMusicResources;
    private List<Tag> mockTags;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockMusicResources = new ArrayList<>();
        MusicResource resource = new MusicResource();
        resource.setId(1L);
        resource.setName("测试音乐");
        mockMusicResources.add(resource);

        mockTags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("测试标签");
        tag.setCreateTime(LocalDateTime.now());
        mockTags.add(tag);
    }

    @Test
    void getMusicByTag_ShouldReturnMusicList() {
        // 准备测试数据
        Long[] tagIds = {1L, 2L};
        when(tagService.getMusicByTag(tagIds)).thenReturn(Result.success(mockMusicResources));

        // 执行测试
        Result<List<MusicResource>> result = tagController.get(tagIds);

        // 验证结果
        verify(tagService).getMusicByTag(tagIds);
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockMusicResources, result.getData());
    }

    @Test
    void getTags_ShouldReturnTagList() {
        // 准备测试数据
        when(tagService.getTags()).thenReturn(Result.success(mockTags));

        // 执行测试
        Result<List<Tag>> result = tagController.getTags();

        // 验证结果
        verify(tagService).getTags();
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockTags, result.getData());
    }
}