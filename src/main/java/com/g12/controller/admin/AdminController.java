package com.g12.controller.admin;


import com.g12.dto.AdminLoginDTO;
import com.g12.dto.UserLoginDTO;
import com.g12.result.Result;
import com.g12.service.AdminService;
import com.g12.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody AdminLoginDTO adminLoginDTO) {

        return adminService.login(adminLoginDTO);
    }

    /**
     * 返回用户和音乐资源数据
     * @return
     */
    @GetMapping("/statistic")
    public Result<StatisticsVO> getStatistics() {
        StatisticsVO statistics = adminService.getStatistics();
        return Result.success(statistics);
    }
}
