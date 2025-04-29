package com.g12.service.impl;


import com.g12.dto.AdminLoginDTO;
import com.g12.entity.Admin;
import com.g12.mapper.AdminMapper;
import com.g12.result.Result;
import com.g12.service.AdminService;
import com.g12.vo.DailyNewMusicVO;
import com.g12.vo.DailyUserCountVO;
import com.g12.vo.StatisticsVO;
import com.g12.vo.TagDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Result login(AdminLoginDTO adminLoginDTO) {
        // 查询管理员账号
        Admin admin = adminMapper.login(adminLoginDTO);

        if (admin == null) {
            return Result.error("账号错误");
        }

        // 密码校验
        String inputPassword = adminLoginDTO.getPassword();
        System.out.println(inputPassword);
        System.out.println(admin.getPassword());
        if (!inputPassword.equals(admin.getPassword())) {
            return Result.error("密码错误");
        }else {
            return Result.success("登录成功: " + admin);
        }
    }

    @Override
    public StatisticsVO getStatistics() {
        Long userCount = adminMapper.countUsers();
        Long musicResourceCount = adminMapper.countMusicResources();
        return new StatisticsVO(userCount, musicResourceCount);
    }

    @Override
    public List<TagDataVO> countMusicResourcesByTag() {
        return adminMapper.countMusicResourcesByTag();
    }

    @Override
    public List<DailyUserCountVO> getDailyNewUserCount() {
        return adminMapper.countDailyNewUsers();
    }

    @Override
    public List<DailyUserCountVO> getDailyTotalUserCount() {
        return adminMapper.countDailyTotalUsers();
    }

    @Override
    public List<DailyNewMusicVO> getDailyNewMusic() {
        return adminMapper.countDailyNewMusic();
    }

    @Override
    public Integer getBlockUsers(){
        return adminMapper.countBlockUsers();
    }

    @Override
    public Integer getBlockMusics(){
        return adminMapper.countBlockMusics();
    }
}