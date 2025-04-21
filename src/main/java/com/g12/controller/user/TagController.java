package com.g12.controller.user;

import com.g12.dto.MusicTagDTO;
import com.g12.entity.MusicResource;
import com.g12.result.Result;
import com.g12.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    TagService tagService;

    /**
     * 创建tag
     * @param musicTagDTO
     * @return
     */
    @PostMapping
    public Result addTag(MusicTagDTO musicTagDTO){
        return tagService.addTag(musicTagDTO);
    }


    /**
     * 根据tag筛选获取歌曲
     * @param tagName
     * @return
     */
    @GetMapping
    public Result<List<MusicResource>> get(String tagName){

        return tagService.getMusicByTag(tagName);
    }
}
