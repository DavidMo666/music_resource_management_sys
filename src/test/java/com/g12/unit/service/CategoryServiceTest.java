package com.g12.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.g12.entity.MusicResource;
import com.g12.mapper.CategoryMapper;
import com.g12.mapper.MusicResourceCategoryMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.impl.CategoryServiceImpl;
import com.github.pagehelper.Page;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private MusicResourceCategoryMapper musicResourceCategoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private MusicCategory mockCategory;
    private Page<MusicCategory> mockPage;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockCategory = new MusicCategory();
        mockCategory.setId(1L);
        mockCategory.setName("测试分类");
        mockCategory.setDescription("测试分类描述");
        mockCategory.setUserId(1L);
        mockCategory.setCreateTime(LocalDateTime.now());
        mockCategory.setUpdateTime(LocalDateTime.now());

        // 创建模拟分页数据
        mockPage = new Page<>();
        List<MusicCategory> categories = new ArrayList<>();
        categories.add(mockCategory);
        mockPage.addAll(categories);
        mockPage.setTotal(1);
    }

    @Test
    void deleteCategory_ShouldDeleteAndReturnCount() {
        // 准备测试数据
        when(categoryMapper.deleteCategory(anyInt())).thenReturn(1);

        // 执行测试
        int result = categoryService.deleteCategory(1L);

        // 验证结果
        assertEquals(1, result);
        verify(categoryMapper).deleteCategory(1);
    }

    @Test
    void pageQuery_ShouldReturnPageResult() {
        // 准备测试数据
        CategoryPageQueryDTO queryDTO = new CategoryPageQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setPageSize(10);
        when(categoryMapper.pageQuery(any(CategoryPageQueryDTO.class))).thenReturn(mockPage);

        // 执行测试
        PageResult result = categoryService.pageQuery(queryDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
    }

    @Test
    void save_ShouldSaveCategory() {
        // 执行测试
        categoryService.save(mockCategory);

        // 验证结果
        verify(categoryMapper).insert(mockCategory);
    }

    @Test
    void update_ShouldUpdateCategory() {
        // 执行测试
        categoryService.update(mockCategory);

        // 验证结果
        assertNotNull(mockCategory.getUpdateTime());
        verify(categoryMapper).update(mockCategory);
    }

    @Test
    void getMusicInCategory_ShouldReturnMusicList() {
        // 准备测试数据
        List<MusicResource> mockMusicList = new ArrayList<>();
        MusicResource mockMusic = new MusicResource();
        mockMusic.setId(1L);
        mockMusic.setName("测试音乐");
        mockMusicList.add(mockMusic);
        
        when(categoryMapper.getMusicInCategory(anyLong())).thenReturn(mockMusicList);
        
        // 执行测试
        Result<List<MusicResource>> result = categoryService.getMusicInCategory(1L);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(1, result.getData().size());
        assertEquals("测试音乐", result.getData().get(0).getName());
    }

    @Test
    void getMusicInCategory_WithEmptyResult_ShouldReturnEmptyList() {
        // 准备测试数据 - 空结果
        when(categoryMapper.getMusicInCategory(anyLong())).thenReturn(new ArrayList<>());
        
        // 执行测试
        Result<List<MusicResource>> result = categoryService.getMusicInCategory(1L);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertTrue(result.getData().isEmpty());
    }

    @Test
    void addMusicToCategory_ShouldAddSuccessfully() {
        // 准备测试数据
        when(musicResourceCategoryMapper.count(any())).thenReturn(0);
        
        // 执行测试
        Result result = categoryService.addMusicToCategory(1L, 1L);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        verify(musicResourceCategoryMapper).addMusicToCategory(any());
    }

    @Test
    void addMusicToCategory_WhenMusicAlreadyExists_ShouldReturnError() {
        // 准备测试数据 - 音乐已存在
        when(musicResourceCategoryMapper.count(any())).thenReturn(1);
        
        // 执行测试
        Result result = categoryService.addMusicToCategory(1L, 1L);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("歌单已有此歌曲", result.getMsg());
        verify(musicResourceCategoryMapper, never()).addMusicToCategory(any());
    }

    @Test
    void removeMusic_ShouldRemoveSuccessfully() {
        // 准备测试数据
        Long[] musicIds = {1L, 2L};
        Long categoryId = 1L;
        
        // 执行测试
        Result result = categoryService.removeMusic(musicIds, categoryId);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        verify(musicResourceCategoryMapper).removeMusic(musicIds, categoryId);
    }

    @Test
    void getLatest_ShouldReturnLatestCategories() {
        // 准备测试数据
        List<MusicCategory> mockCategories = new ArrayList<>();
        mockCategories.add(mockCategory);
        when(categoryMapper.getLatest()).thenReturn(mockCategories);
        
        // 执行测试
        Result<List<MusicCategory>> result = categoryService.getLatest();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(1, result.getData().size());
    }
}