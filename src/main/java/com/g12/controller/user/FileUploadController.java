package com.g12.controller.user;

import com.g12.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    @Value("${file.avatar-dir}")
    private String avatarDir;

    @Value("${file.music-dir}")
    private String musicDir;


    private String hostName = "http://121.199.71.16";

    /**
     * 上传头像
     * @param avatarFile
     * @return
     */
    @PostMapping("/upload/avatar")
    public Result uploadAvatar(MultipartFile avatarFile) throws IOException {

        String originalFilename = avatarFile.getOriginalFilename();

        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        String path = avatarDir + fileName;
        String url = hostName +avatarDir + fileName;

        avatarFile.transferTo(new File(path));

        return Result.success(url);
    }
}
