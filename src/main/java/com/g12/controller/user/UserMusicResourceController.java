package com.g12.controller.user;

import com.g12.entity.MusicResource;
import com.g12.result.Result;
import com.g12.service.MusicResourceService;
import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user/music-resource")
public class UserMusicResourceController {

    @Autowired
    private MusicResourceService musicResourceService;

    /**
     * 新增音乐资源
     * @param musicResource 音乐资源信息
     * @return 操作结果
     */
    @PostMapping
    public Result<String> addMusicResource(@RequestBody MusicResource musicResource) {
        // 参数校验
        if (StringUtils.isEmpty(musicResource.getName())) {
            return Result.error("音乐名称不能为空");
        }
        if (StringUtils.isEmpty(musicResource.getImage())) {
            return Result.error("音乐封面不能为空");
        }
        if (musicResource.getUploadUserId() == null || musicResource.getUploadUserId() <= 0) {
            return Result.error("上传用户ID无效");
        }
        if (StringUtils.isEmpty(musicResource.getUrl())) {
            return Result.error("音乐文件路径不能为空");
        }

        try {
            // 设置默认值和必要字段
            if (musicResource.getStatus() == null) {
                musicResource.setStatus(1); // 默认状态为正常
            }
            musicResource.setUploadTime(LocalDateTime.now());

            // 调用服务层
            Result<String> result = musicResourceService.addMusicResource(musicResource);
            if (result.getCode() == 1) {
                log.info("音乐资源添加成功: {}", musicResource.getName());
            }
            return result;
        } catch (Exception e) {
            log.error("添加音乐资源失败", e);
            return Result.error("系统繁忙，请稍后重试");
        }
    }
}
