package com.g12.unit.service;

import com.g12.context.BaseContext;
import com.g12.entity.MusicFavourite;
import com.g12.entity.MusicResource;
import com.g12.mapper.FavouriteMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.impl.FavouriteServiceImpl;
import com.github.pagehelper.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FavouriteServiceTest {

    @Mock
    private FavouriteMapper favouriteMapper;

    @InjectMocks
    private FavouriteServiceImpl favouriteService;

    private final Long USER_ID = 1L;
    private final Long MUSIC_ID = 100L;
    private Page<MusicResource> musicPage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 准备测试数据
        musicPage = new Page<>();
        List<MusicResource> list = new ArrayList<>();
        MusicResource music = new MusicResource();
        music.setId(MUSIC_ID);
        music.setName("测试音乐");
        list.add(music);
        musicPage.addAll(list);
        musicPage.setTotal(1);
    }

    @Test
    public void add_ShouldAddMusicToFavourites() {
        // 模拟BaseContext.getCurrentId()返回用户ID
        try (MockedStatic<BaseContext> baseContextMockedStatic = mockStatic(BaseContext.class)) {
            baseContextMockedStatic.when(BaseContext::getCurrentId).thenReturn(USER_ID);

            // 调用被测试方法
            Result result = favouriteService.add(MUSIC_ID);

            // 验证结果
            assertNotNull(result);
            assertEquals(1, result.getCode());

            // 验证方法调用
            verify(favouriteMapper, times(1)).add(any(MusicFavourite.class));
        }
    }

    @Test
    public void remove_ShouldRemoveMusicFromFavourites() {
        // 模拟BaseContext.getCurrentId()返回用户ID
        try (MockedStatic<BaseContext> baseContextMockedStatic = mockStatic(BaseContext.class)) {
            baseContextMockedStatic.when(BaseContext::getCurrentId).thenReturn(USER_ID);

            // 调用被测试方法
            Result result = favouriteService.remove(MUSIC_ID);

            // 验证结果
            assertNotNull(result);
            assertEquals(1, result.getCode());

            // 验证方法调用
            verify(favouriteMapper, times(1)).delete(any(MusicFavourite.class));
        }
    }

    @Test
    public void get_ShouldReturnPagedFavourites() {
        // 模拟BaseContext.getCurrentId()返回用户ID
        try (MockedStatic<BaseContext> baseContextMockedStatic = mockStatic(BaseContext.class)) {
            baseContextMockedStatic.when(BaseContext::getCurrentId).thenReturn(USER_ID);

            // 模拟favouriteMapper.get返回分页结果
            when(favouriteMapper.get(eq(USER_ID))).thenReturn(musicPage);

            // 调用被测试方法
            Result<PageResult> result = favouriteService.get(1, 10);

            // 验证结果
            assertNotNull(result);
            assertEquals(1, result.getCode());
            assertEquals(1, result.getData().getTotal());
            assertEquals(1, result.getData().getRecords().size());

            // 验证方法调用
            verify(favouriteMapper, times(1)).get(eq(USER_ID));
        }
    }
}