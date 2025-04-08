package com.g12.controller.user;

import com.g12.dto.UserLoginDTO;
import com.g12.result.Result;
import com.g12.service.LoginService;
import com.g12.vo.CaptchaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/captcha")
    public Result<CaptchaVO> getCaptcha(){

        CaptchaVO captchaVo = loginService.getCaptcha();

        return Result.success(captchaVo);
    }

    @PostMapping
    public Result login(UserLoginDTO userLoginDTO){

        return loginService.login(userLoginDTO);

    }
}
