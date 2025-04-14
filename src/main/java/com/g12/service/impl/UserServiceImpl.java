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

import java.util.List;

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

        Page<User> pages = userMapper.pageQuery(userPageQueryDto.getUserName());

        PageResult pageResult = new PageResult();
        pageResult.setTotal(pages.getTotal());
        pageResult.setRecords(pages.getResult());

        return pageResult;
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        if (userMapper.selectById(id) == null) {
            throw new RuntimeException("用户: " + id + " 不存在");
        }
        userMapper.deleteById(id);
    }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return User Information
     */
    @Override
    public User getByUsername(String username) {
        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名: " + username + " 不存在");
        }
        return user;
    }



}
