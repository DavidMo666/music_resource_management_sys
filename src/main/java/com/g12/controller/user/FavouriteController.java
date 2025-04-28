package com.g12.controller.user;

import com.g12.entity.MusicResource;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourite")
public class FavouriteController {

    @Autowired
    FavouriteService favouriteService;

    /**
     * 增
     * @param musicId
     * @return
     */
    @PostMapping
    public Result add(Long musicId){
        return favouriteService.add(musicId);
    }

    /**
     * 删除
     * @param musicId
     * @return
     */
    @DeleteMapping
    public Result remove(Long musicId){
        return favouriteService.remove(musicId);
    }

    @GetMapping
    public Result<PageResult> get(int page, int pageSize){
        return favouriteService.get(page, pageSize);
    }


}
