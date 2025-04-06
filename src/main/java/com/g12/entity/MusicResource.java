package com.g12.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "music_resource")
@Entity
public class MusicResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id; // music_id

    private String name; // 资源名称

    private Integer type; // 资源类型（1:mp3, 2:...）

    private String url; // 资源的存储路径或 URL

    private Long size; // 资源文件的大小（单位：字节）

    private String artist; // 演唱者/艺术家

    private String album; // 专辑名

    private String genre; // 音乐类型（如：流行、摇滚等）

    private String duration; // 时长

    private String createTime; // 创建时间

    private String updateTime; // 更新时间

    private String image; // 音乐资源图片路径

    private String description; // 音乐资源描述

    private Integer status; // 音乐资源状态

    @Column(name = "upload_user_id")
    private Integer uploadUserId; // 上传用户的ID
}