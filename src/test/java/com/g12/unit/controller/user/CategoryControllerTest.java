package com.g12.unit.controller.user;

import com.g12.controller.user.CategoryController;
import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.CategoryService;
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
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MusicCategory mockCategory;

    @BeforeEach
    void setUp() {
        mockCategory = new MusicCategory();
        mockCategory.setId(1L);
        mockCategory.setName("测试分类");
        mockCategory.setDescription("测试分类描述");
        mockCategory.setUserId(1L);
        mockCategory.setCreateTime(LocalDateTime.now());
        mockCategory.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void deleteCategory_ShouldDeleteSuccessfully() {
        // 准备测试数据
        Long categoryId = 1L;
        when(categoryService.deleteCategory(categoryId)).thenReturn(1);

        // 执行测试
        Result<String> result = categoryController.deleteCategory(categoryId);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
    }

    @Test
    void pageQuery_ShouldReturnPageResult() {
        // 准备测试数据
        CategoryPageQueryDTO queryDTO = new CategoryPageQueryDTO();
        PageResult mockPageResult = new PageResult();
        List<MusicCategory> records = new ArrayList<>();
        records.add(mockCategory);
        mockPageResult.setRecords(records);
        mockPageResult.setTotal(1L);

        when(categoryService.pageQuery(queryDTO)).thenReturn(mockPageResult);

        // 执行测试
        Result result = categoryController.pageQuery(queryDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockPageResult, result.getData());
    }

    @Test
    void pageQueryAll_ShouldReturnPageResult() {
        // 准备测试数据
        CategoryPageQueryDTO queryDTO = new CategoryPageQueryDTO();
        PageResult mockPageResult = new PageResult();
        List<MusicCategory> records = new ArrayList<>();
        records.add(mockCategory);
        mockPageResult.setRecords(records);
        mockPageResult.setTotal(1L);
    
        when(categoryService.pageQueryAll(queryDTO)).thenReturn(mockPageResult);
    
        // 执行测试
        Result result = categoryController.pageQueryAll(queryDTO);
    
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockPageResult, result.getData());
    }

    @Test
    void addMusicToCategory_ShouldAddSuccessfully() {
        // 准备测试数据
        Long categoryId = 1L;
        Long musicId = 1L;
        when(categoryService.addMusicToCategory(categoryId, musicId)).thenReturn(Result.success());
    
        // 执行测试
        Result result = categoryController.addMusicToCategory(categoryId, musicId);
    
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        verify(categoryService).addMusicToCategory(categoryId, musicId);
    }

    @Test
    void removeMuisc_ShouldRemoveSuccessfully() {
        // 准备测试数据
        Long[] musicIds = {1L, 2L};
        Long categoryId = 1L;
        when(categoryService.removeMusic(musicIds, categoryId)).thenReturn(Result.success());
    
        // 执行测试
        Result result = categoryController.removeMuisc(musicIds, categoryId);
    
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        verify(categoryService).removeMusic(musicIds, categoryId);
    }

    @Test
    void getLatest_ShouldReturnLatestCategories() {
        // 准备测试数据
        List<MusicCategory> mockList = new ArrayList<>();
        mockList.add(mockCategory);
        when(categoryService.getLatest()).thenReturn(Result.success(mockList));
    
        // 执行测试
        Result<List<MusicCategory>> result = categoryController.getLatest();
    
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(mockList, result.getData());
    }

    @Test
    void add_ShouldAddSuccessfully() {
        // 执行测试
        Result<String> result = categoryController.add(mockCategory);

        // 验证结果
        verify(categoryService).save(mockCategory);
        assertNotNull(result);
        assertEquals(1, result.getCode());
    }

    @Test
    void updateCategory_ShouldUpdateSuccessfully() {
        // 执行测试
        Result<String> result = categoryController.updateCategory(mockCategory);

        // 验证结果
        verify(categoryService).update(mockCategory);
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals("更新成功", result.getData());
    }

    @Test
    void add_WithEmptyName_ShouldReturnError() {
        // 准备测试数据
        MusicCategory category = new MusicCategory();
        category.setName(""); // 空名称
    
        // 执行测试
        Result<String> result = categoryController.add(category);
    
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("分类名称不能为空", result.getMsg());
        
        // 验证服务未被调用
        verify(categoryService, never()).save(any(MusicCategory.class));
    }

    @Test
    void updateCategory_WithNullId_ShouldReturnError() {
        // 准备测试数据
        MusicCategory category = new MusicCategory();
        category.setName("测试分类");
        // ID为null
    
        // 执行测试
        Result<String> result = categoryController.updateCategory(category);
    
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("分类ID不能为空", result.getMsg());
        
        // 验证服务未被调用
        verify(categoryService, never()).update(any(MusicCategory.class));
    }
}