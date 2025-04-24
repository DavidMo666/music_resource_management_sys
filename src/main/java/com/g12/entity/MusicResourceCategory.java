package com.g12.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicResourceCategory {

    Long id;
    Long musicId;
    Long categoryId;
    LocalDateTime createTime;
}
