package com.g12.controller.admin;


import com.g12.dto.AdminLoginDTO;
import com.g12.dto.UserLoginDTO;
import com.g12.result.Result;
import com.g12.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody AdminLoginDTO adminLoginDTO) {

        return adminService.login(adminLoginDTO);
    }
}
