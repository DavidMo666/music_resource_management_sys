package com.g12.service;

import com.g12.dto.UserPageQueryDTO;
import com.g12.result.PageResult;

public interface UserService {

    /**
     * 更新用户状态
     *
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 分页查询
     * @param userPageQueryDto
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDto);


}
