package com.g12.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicResource {

    @Id
    @Column(name = "music_id")
    private int id;               // 音乐资源id

    @Column(name = "name")
    private String name;          // 音乐资源名称

    @Column(name = "type")
    private int type;             // 音乐资源的类型 (1:mp3, 2:...)

    @Column(name = "album")
    private String album;         // 专辑

    @Column(name = "image")
    private String image;         // 音乐资源图片路径

    @Column(name = "description")
    private String description;   // 音乐资源描述

    @Column(name = "status")
    private int status;           // 音乐资源状态

    @Column(name = "create_time")
    private String createTime;    // 创建时间

    @Column(name = "update_time")
    private String updateTime;    // 更新时间

    @Column(name = "upload_user_id")
    private int uploadUserId;     // 上传用户ID

    @Column(name = "artist")
    private String singer;        // 歌手（数据库中是artist）

    @Column(name = "duration")
    private String duration;      // 时长

    @Column(name = "genre")
    private String genre;         // 音乐类型

    @Column(name = "size")
    private Long size;           // 文件大小

    @Column(name = "url")
    private String url;          // 资源URL
}
