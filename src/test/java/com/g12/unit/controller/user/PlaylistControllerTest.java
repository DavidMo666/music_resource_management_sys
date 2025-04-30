package com.g12.unit.controller.user;

import com.g12.controller.user.PlaylistController;
import com.g12.entity.MusicResource;
import com.g12.result.Result;
import com.g12.service.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlaylistControllerTest {

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private PlaylistController playlistController;

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
        // 准备模拟数据
        Result<List<MusicResource>> mockResult = Result.success(musicList);
        when(playlistService.getMusicInPlayList()).thenReturn(mockResult);

        // 调用被测试方法
        Result<List<MusicResource>> result = playlistController.getMusicInPlayList();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(1, result.getData().size());
        assertEquals(MUSIC_ID, result.getData().get(0).getId());

        // 验证service方法被调用
        verify(playlistService, times(1)).getMusicInPlayList();
    }

    @Test
    public void addMusic_ShouldCallServiceAddMusic() {
        // 准备模拟数据
        Result mockResult = Result.success();
        when(playlistService.addMusic(eq(MUSIC_ID))).thenReturn(mockResult);

        // 调用被测试方法
        Result result = playlistController.addMusic(MUSIC_ID);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());

        // 验证service方法被调用
        verify(playlistService, times(1)).addMusic(eq(MUSIC_ID));
    }

    @Test
    public void deleteMusic_ShouldCallServiceDeleteMusic() {
        // 准备模拟数据
        Result mockResult = Result.success();
        when(playlistService.deleteMusic(eq(MUSIC_ID))).thenReturn(mockResult);

        // 调用被测试方法
        Result result = playlistController.deleteMusic(MUSIC_ID);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());

        // 验证service方法被调用
        verify(playlistService, times(1)).deleteMusic(eq(MUSIC_ID));
    }
}