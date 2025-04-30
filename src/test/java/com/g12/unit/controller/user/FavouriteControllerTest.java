package com.g12.unit.controller.user;

import com.g12.controller.user.FavouriteController;
import com.g12.entity.MusicResource;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.FavouriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FavouriteControllerTest {

    @Mock
    private FavouriteService favouriteService;

    @InjectMocks
    private FavouriteController favouriteController;

    private final Long MUSIC_ID = 100L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void add_ShouldCallServiceAdd() {
        // 准备模拟数据
        Result mockResult = Result.success();
        when(favouriteService.add(eq(MUSIC_ID))).thenReturn(mockResult);

        // 调用被测试方法
        Result result = favouriteController.add(MUSIC_ID);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());

        // 验证service方法被调用
        verify(favouriteService, times(1)).add(eq(MUSIC_ID));
    }

    @Test
    public void remove_ShouldCallServiceRemove() {
        // 准备模拟数据
        Result mockResult = Result.success();
        when(favouriteService.remove(eq(MUSIC_ID))).thenReturn(mockResult);

        // 调用被测试方法
        Result result = favouriteController.remove(MUSIC_ID);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());

        // 验证service方法被调用
        verify(favouriteService, times(1)).remove(eq(MUSIC_ID));
    }

    @Test
    public void get_ShouldCallServiceGet() {
        // 准备模拟数据
        int page = 1;
        int pageSize = 10;
        List<MusicResource> musicList = new ArrayList<>();
        MusicResource music = new MusicResource();
        music.setId(MUSIC_ID);
        music.setName("测试音乐");
        musicList.add(music);
        PageResult pageResult = new PageResult(1L, musicList);
        Result<PageResult> mockResult = Result.success(pageResult);

        when(favouriteService.get(eq(page), eq(pageSize))).thenReturn(mockResult);

        // 调用被测试方法
        Result<PageResult> result = favouriteController.get(page, pageSize);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(1, result.getData().getTotal());
        assertEquals(1, result.getData().getRecords().size());

        // 验证service方法被调用
        verify(favouriteService, times(1)).get(eq(page), eq(pageSize));
    }
}