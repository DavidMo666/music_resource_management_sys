package com.g12.controller.admin;


import ch.qos.logback.core.util.StringUtil;
import com.g12.dto.UserPageQueryDto;
import com.g12.entity.User;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.UserService;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public Result pageQuery(UserPageQueryDto userPageQueryDto){

        log.info("分页查询");

        PageResult pageResult = userService.pageQuery(userPageQueryDto);

        return Result.success(pageResult);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return Result.success("已删除id为 " + id + " 的用户");
    }
    
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return User Information
     */
    @Operation(summary = "根据用户名查询用户信息")
    @GetMapping("/username/{username}")
    public Result<User> getByUsername(@PathVariable String username) {
        log.info("根据用户名查询用户信息: {}", username);
        User user = userService.getByUsername(username);
        return Result.success(user);
    }

     /**
     * 根据id查询用户信息
     * @param id
     * @return User Information
     */
    @Operation(summary = "根据id查询用户信息")
    @GetMapping("/id/{id}")
    public Result<User> getById(@PathVariable Long id) {
        log.info("根据id查询用户信息: {}", id);
        User user = userService.getById(id);
        return Result.success(user);
    }
}
