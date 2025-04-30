package com.g12.unit.service;

import com.g12.context.BaseContext;
import com.g12.entity.MusicResource;
import com.g12.entity.PlaylistItem;
import com.g12.mapper.PlaylistMapper;
import com.g12.result.Result;
import com.g12.service.impl.PlayListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlayListServiceTest {

    @Mock
    private PlaylistMapper playlistMapper;

    @InjectMocks
    private PlayListServiceImpl playlistService;

    private final Long USER_ID = 1L;
    private final Long MUSIC_ID = 100L;
    private List<MusicResource> musicList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 准备测试数据
        musicList = new ArrayList<>();
        MusicResource music = new MusicResource();
        music.setId(MUSIC_ID);
        music.setName("测试音乐");
        musicList.add(music);
    }

    @Test
    public void getMusicInPlayList_ShouldReturnMusicList() {
        // 模拟BaseContext.getCurrentId()返回用户ID
        try (MockedStatic<BaseContext> baseContextMockedStatic = mockStatic(BaseContext.class)) {
            baseContextMockedStatic.when(BaseContext::getCurrentId).thenReturn(USER_ID);

            // 模拟playlistMapper.getMusic返回音乐列表
            when(playlistMapper.getMusic(eq(USER_ID))).thenReturn(musicList);

            // 调用被测试方法
            Result<List<MusicResource>> result = playlistService.getMusicInPlayList();

            // 验证结果
            assertNotNull(result);
            assertEquals(1, result.getCode());
            assertEquals(1, result.getData().size());
            assertEquals(MUSIC_ID, result.getData().get(0).getId());

            // 验证方法调用
            verify(playlistMapper, times(1)).getMusic(eq(USER_ID));
        }
    }

    @Test
    public void addMusic_ShouldAddMusicToPlaylist() {
        // 模拟BaseContext.getCurrentId()返回用户ID
        try (MockedStatic<BaseContext> baseContextMockedStatic = mockStatic(BaseContext.class)) {
            baseContextMockedStatic.when(BaseContext::getCurrentId).thenReturn(USER_ID);

            // 调用被测试方法
            Result result = playlistService.addMusic(MUSIC_ID);

            // 验证结果
            assertNotNull(result);
            assertEquals(1, result.getCode());

            // 验证方法调用
            verify(playlistMapper, times(1)).delete(any(PlaylistItem.class));
            verify(playlistMapper, times(1)).add(any(PlaylistItem.class));
        }
    }

    @Test
    public void deleteMusic_ShouldRemoveMusicFromPlaylist() {
        // 模拟BaseContext.getCurrentId()返回用户ID
        try (MockedStatic<BaseContext> baseContextMockedStatic = mockStatic(BaseContext.class)) {
            baseContextMockedStatic.when(BaseContext::getCurrentId).thenReturn(USER_ID);

            // 调用被测试方法
            Result result = playlistService.deleteMusic(MUSIC_ID);

            // 验证结果
            assertNotNull(result);
            assertEquals(1, result.getCode());

            // 验证方法调用
            verify(playlistMapper, times(1)).delete(any(PlaylistItem.class));
        }
    }
}