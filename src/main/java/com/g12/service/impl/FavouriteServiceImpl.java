package com.g12.service.impl;

import com.g12.context.BaseContext;
import com.g12.entity.MusicFavourite;
import com.g12.entity.MusicResource;
import com.g12.mapper.FavouriteMapper;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.FavouriteService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    FavouriteMapper favouriteMapper;

    /**
     * 添加
     * @param musicId
     * @return
     */
    @Override
    public Result add(Long musicId) {
        Long userId = BaseContext.getCurrentId();

        MusicFavourite favourite = new MusicFavourite();
        favourite.setCreateTime(LocalDateTime.now());
        favourite.setUserId(userId);
        favourite.setMusicId(musicId);
        favouriteMapper.add(favourite);

        return Result.success();
    }

    @Override
    public Result remove(Long musicId) {
        Long userId = BaseContext.getCurrentId();

        MusicFavourite favourite = new MusicFavourite();
        favourite.setUserId(userId);
        favourite.setMusicId(musicId);
        favouriteMapper.delete(favourite);

        return Result.success();
    }

    @Override
    public Result<PageResult> get(int page, int pageSize) {

        PageHelper.startPage(page, pageSize);

        Long userId = BaseContext.getCurrentId();

        Page<MusicResource> pages = favouriteMapper.get(userId);

        PageResult pageResult = new PageResult(pages.getTotal(),pages.getResult());

        return Result.success(pageResult);
    }
}
