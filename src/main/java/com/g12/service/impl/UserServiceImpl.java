package com.g12.service.impl;

import com.g12.dto.UserPageQueryDto;
import com.g12.entity.User;
import com.g12.mapper.UserMapper;
import com.g12.result.PageResult;
import com.g12.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {

        User user = new User();
        user.setId(id);
        user.setStatus(status);

        userMapper.update(user);
    }

    /**
     * 分页查询
     * @param userPageQueryDto
     * @return
     */
    @Override
    public PageResult pageQuery(UserPageQueryDto userPageQueryDto) {

        PageHelper.startPage(userPageQueryDto.getPage(), userPageQueryDto.getPageSize());

        Page<User> pages = userMapper.pageQuery(userPageQueryDto.getUsername());

        PageResult pageResult = new PageResult();
        pageResult.setTotal(pages.getTotal());
        pageResult.setRecords(pages.getResult());

        return pageResult;
    }
}
