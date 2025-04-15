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
@RequestMapping("/user/resource")
public class UserMusicResourceController {

    @Autowired
    MusicResourceService musicResourceService;

    /**
     * 批量删除音乐资源
     * @param ids 以逗号分隔的资源 ID 字符串
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result deleteMusicResource(@RequestParam("ids") String ids) {
        // 参数校验
        if (StringUtils.isEmpty(ids)) {
            return Result.error("参数ids不能为空");
        }

        // 转换参数为ID列表
        List<Integer> idsArray;
        try {
            // 首先检查ids参数是否只包含数字
            if (!ids.matches("^[0-9]+(,[0-9]+)*$")) {
                return Result.error("参数包含非法字符");
            }

            // 将ids字符串拆分并转换为List
            idsArray = Arrays.stream(ids.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return Result.error("参数包含非法字符");
        }

        // 执行删除操作
        try {
            int count = musicResourceService.batchDeleteResources(idsArray);
            if (count != idsArray.size()){
                return Result.success("成功删除" + count + "条数据" + ", 但有" + (idsArray.size()-count) + "条数据不在数据库中");
            }else {
                return Result.success("成功删除" + count + "条数据");
            }
        } catch (Exception e) {
            return Result.error("删除操作失败");
        }
    }

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
