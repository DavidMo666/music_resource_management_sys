package com.g12.controller.admin;

import com.g12.service.MusicResource;
import com.g12.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MusicResourceController {

    @Autowired
    private MusicResource musicResource;

    /**
     * 批量删除音乐资源
     * @param ids 以逗号分隔的资源 ID 字符串
     * @return 删除结果
     */
    @DeleteMapping("/admin/resource/delete")
    public Result deleteMusicResource(@RequestParam("ids") String ids) {
        // 处理 ids 参数，转换为 List<Integer>
        List<Integer> idsList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int deletedCount = musicResource.batchDeleteResources(idsList);
        return Result.success("成功删除 " + deletedCount + " 个资源");
    }
}
