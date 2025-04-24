package com.g12.controller.user;

import com.g12.dto.MusicTagDTO;
import com.g12.entity.MusicResource;
import com.g12.entity.Tag;
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
     * tag
     * @param
     * @return
     */
//    @PostMapping
//    public Result addTag(MusicTagDTO[] musicTagDTOs){
//        return tagService.addTag(musicTagDTOs);
//    }



    /**
     * 根据tag筛选获取歌曲
     * @param tagIds
     * @return
     */
    @GetMapping("/music")
    public Result<List<MusicResource>> get(Long[] tagIds){
        return tagService.getMusicByTag(tagIds);
    }


    /**
     * 获取所有tag
     * @return
     */
    @GetMapping
    public Result<List<Tag>> getTags(){
        return tagService.getTags();
    }

}
