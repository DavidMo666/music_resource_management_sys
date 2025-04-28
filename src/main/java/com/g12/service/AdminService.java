package com.g12.service;

import com.g12.dto.AdminLoginDTO;
import com.g12.dto.UserLoginDTO;
import com.g12.result.Result;
import com.g12.vo.StatisticsVO;

import javax.security.auth.login.AccountNotFoundException;

public interface AdminService {
    Result login(AdminLoginDTO adminLoginDTO);

    StatisticsVO getStatistics();
}
