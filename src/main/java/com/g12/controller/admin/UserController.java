package com.g12.controller.admin;


import com.g12.entity.User;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.UserService;
import com.g12.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status, Long id){

        log.info("更新状态");

        userService.updateStatus(status, id);

        Result<Object> success = Result.success();

        return success;
    }

}
