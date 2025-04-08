package com.g12.controller.user;

import com.g12.dto.UserLoginDTO;
import com.g12.result.Result;
import com.g12.service.UserService;
import com.g12.vo.CaptchaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("C-UserController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login/captcha")
    public Result<CaptchaVO> getCaptcha(){

        CaptchaVO captchaVo = userService.getCaptcha();

        return Result.success(captchaVo);
    }

    @PostMapping("/login")
    public Result login(UserLoginDTO userLoginDTO){

        return userService.login(userLoginDTO);

    }
}
