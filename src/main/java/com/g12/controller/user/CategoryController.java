package com.g12.controller.user;

import com.g12.dto.CategoryPageQueryDTO;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
