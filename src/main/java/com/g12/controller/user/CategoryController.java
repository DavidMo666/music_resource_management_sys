package com.g12.controller.user;

import com.g12.result.Result;
import com.g12.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteCategory(@PathVariable("id") Long categoryId) {
        try {
            int result = categoryService.deleteCategory(categoryId);
            if (result > 0) {
                return Result.success("成功删除id为"+ categoryId + "的分类");
            } else {
                return Result.error("id为" + categoryId + "的分类不存在");
            }
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }
}
