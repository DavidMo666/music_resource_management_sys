package com.g12.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicTag {

    private Long musicId;
    private Long tagId;
    private LocalDateTime createTime;
    private Long userId;
}
