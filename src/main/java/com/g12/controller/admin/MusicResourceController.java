package com.g12.controller.admin;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.MusicResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/resource")
public class MusicResourceController {

    @Autowired
    MusicResourceService musicResourceService;

    @GetMapping("/page")
    public Result<PageResult> pageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO){

        log.info("音乐资源分页查询:{}", musicResourcePageQueryDTO);

        PageResult pageResult = musicResourceService.pageQuery(musicResourcePageQueryDTO);

        return Result.success(pageResult);
    }
}
