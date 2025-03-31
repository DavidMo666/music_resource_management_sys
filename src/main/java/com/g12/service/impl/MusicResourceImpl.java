package com.g12.service.impl;

import com.g12.mapper.MusicResourceMapper;
import com.g12.service.MusicResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicResourceImpl implements MusicResource {

    // 自动注入 MusicResourceMapper 来执行数据库操作
    @Autowired
    private MusicResourceMapper musicResourceMapper;

    @Override
    public int batchDeleteResources(List<Integer> ids) {
        // 调用 Mapper 层的方法执行批量删除操作
        return musicResourceMapper.batchDeleteResources(ids);
    }
}
