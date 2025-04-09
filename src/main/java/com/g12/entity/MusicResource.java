package com.g12.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicResource {

    private int id;               // 音乐资源id
    private String name;          // 音乐资源名称
    private int type;             // 音乐资源的类型 (1:mp3, 2:...)
    private String album;         // 专辑
    private String image;         // 音乐资源图片路径
    private String description;   // 音乐资源描述
    private int status;           // 音乐资源状态
    private LocalDateTime uploadTime;      // 更新时间
    private int uploadUserId;     // 上传用户的用户名
    private String singer;        // 歌手
    private int duration;         // 时长（秒数）
}
