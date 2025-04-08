package com.g12.controller.admin;


import com.g12.dto.UserPageQueryDTO;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@Slf4j
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;



    /**
     * 启用或禁用用户
     * @param status
     * @param id
     * @return
     */
    @Operation(summary = "启用或禁用用户")
    @PutMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status, Long id){

        log.info("更新状态");

        userService.updateStatus(status, id);

        return Result.success();
    }


    /**
     * 分页查询
     * @param userPageQueryDto
     * @return
     */
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public Result pageQuery(UserPageQueryDTO userPageQueryDto){

        log.info("分页查询");

        PageResult pageResult = userService.pageQuery(userPageQueryDto);

        return Result.success(pageResult);

    }

}
