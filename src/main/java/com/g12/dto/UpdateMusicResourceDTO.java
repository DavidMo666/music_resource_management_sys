package com.g12.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMusicResourceDTO {

    private Integer id;               // 音乐资源id
    private String name;          // 音乐资源名称
    private Integer type;             // 音乐资源的类型 (1:mp3, 2:...)
    private String album;         // 专辑
    private String image;         // 音乐资源图片路径
    private String description;   // 音乐资源描述
    private Integer status;           // 音乐资源状态
    private LocalDateTime uploadTime;      // 更新时间
    private Long userId;     // 上传用户的用户名
    private String singer;        // 歌手
    private Integer duration;         // 时长（秒数）
    private String url;
    private Long[] tagIds;
}
