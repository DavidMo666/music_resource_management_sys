package com.g12.controller.user;

import com.g12.dto.UserDTO;
import com.g12.dto.UserLoginDTO;
import com.g12.dto.UserRegisterDTO;
import com.g12.entity.User;
import com.g12.result.Result;
import com.g12.service.UserService;
import com.g12.vo.CaptchaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("C-UserController")
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登录验证码
     * @return
     */
    @GetMapping("/login/captcha")
    public Result<CaptchaVO> getCaptcha(){

        CaptchaVO captchaVo = userService.getCaptcha();

        return Result.success(captchaVo);
    }

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(UserLoginDTO userLoginDTO){

        return userService.login(userLoginDTO);

    }

    /**
     * 注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(UserRegisterDTO userRegisterDTO){

        userService.register(userRegisterDTO);

        return Result.success();
    }

    /**
     * 注册激活验证
     * @param activeCode
     * @return
     */
    @GetMapping("/register/verify")
    public Result registerVerify(String activeCode){

        return userService.registerVerify(activeCode);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/user")
    public Result getUser(){

        User user = userService.getUser();

        return Result.success(user);
    }

    /**
     * 更改用户信息
     * @param userDTO
     * @return
     */
    @PutMapping("/user/update")
    public Result updateUser(@RequestBody UserDTO userDTO){

        userService.updateUser(userDTO);

        return Result.success();
    }




}