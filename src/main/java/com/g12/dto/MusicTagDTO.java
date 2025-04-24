package com.g12.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicTagDTO {

    private Long musicId;
    private Long tagId; //tag id
}
