package com.g12.controller.user;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.g12.entity.MusicResource;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 删除歌单
     * @param categoryId
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return Result.success("成功删除id为"+ categoryId + "的分类");
    }

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){

        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 新增分类
     * @param category 分类信息
     * @return 操作结果
     */
    @PostMapping
    public Result add(@RequestBody MusicCategory category) {
        // 验证分类名称不能为空
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return Result.error("分类名称不能为空");
        }

        // 调用服务层保存分类
        categoryService.save(category);
        return Result.success();
    }

    /**
     * 修改歌单
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> updateCategory(@RequestBody MusicCategory category) {
        if (category.getId() == null) {
            return Result.error("分类ID不能为空");
        }
        categoryService.update(category);
        return Result.success("更新成功");
    }


    /**
     * 获取歌单里的音乐
     * @param categoryId
     * @return
     */
    @GetMapping("/music")
    public Result<List<MusicResource>> getMusicInCategory(Long categoryId){

        return categoryService.getMusicInCategory(categoryId);
    }

    /**
     * 新增音乐进歌单
     * @param categoryId
     * @param musicId
     * @return
     */
    @PostMapping("addMusic")
    public Result addMusicToCategory(Long categoryId, Long musicId){
        return categoryService.addMusicToCategory(categoryId,musicId);
    }
}
