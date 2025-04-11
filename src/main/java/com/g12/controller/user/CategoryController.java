package com.g12.controller.user;

import com.g12.entity.MusicCategory;
import com.g12.result.Result;
import com.g12.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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
