package com.g12.controller.user;

import com.g12.result.Result;
import com.g12.util.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    AliOssUtil aliOssUtil;

    /**
     * 上传头像
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result uploadAvatar(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();

        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        String url = aliOssUtil.upload(file.getBytes(), fileName);

        return Result.success(url);
    }
}
