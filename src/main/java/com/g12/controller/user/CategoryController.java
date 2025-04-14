package com.g12.controller.user;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.entity.MusicCategory;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("C-CategoryContoller")
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

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
    public Result<String> add(@RequestBody MusicCategory category) {
        // 验证分类名称不能为空
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return Result.error("分类名称不能为空");
        }

        // 调用服务层保存分类
        categoryService.save(category);
        return Result.success("分类添加成功");
    }
}
