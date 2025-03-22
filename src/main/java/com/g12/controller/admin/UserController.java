package com.g12.controller.admin;


import com.g12.entity.User;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.UserService;
import com.g12.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result listAllUser(){


        return Result.success();
    }

}
