package com.g12.controller.user;

import com.g12.entity.MusicResource;
import com.g12.result.Result;
import com.g12.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    /**
     * 播放列表的音乐
     * @return
     */
    @GetMapping
    public Result<List<MusicResource>> getMusicInPlayList(){
        return  playlistService.getMusicInPlayList();
    }

    /**
     * 增
     * @param musicId
     * @return
     */
    @PostMapping
    public Result addMusic(Long musicId){
        return playlistService.addMusic(musicId);
    }

    /**
     * 删
     * @param musicId
     * @return
     */
    @DeleteMapping
    public Result deleteMusic(Long musicId){
        return playlistService.deleteMusic(musicId);
    }
}
