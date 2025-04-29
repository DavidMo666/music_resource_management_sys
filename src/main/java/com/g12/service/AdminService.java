package com.g12.service;

import com.g12.dto.AdminLoginDTO;
import com.g12.dto.UserLoginDTO;
import com.g12.result.Result;
import com.g12.vo.StatisticsVO;
import com.g12.vo.TagDataVO;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AdminService {
    Result login(AdminLoginDTO adminLoginDTO);

    StatisticsVO getStatistics();

    List<TagDataVO> countMusicResourcesByTag();
}
