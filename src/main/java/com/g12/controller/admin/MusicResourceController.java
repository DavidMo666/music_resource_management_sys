package com.g12.controller.admin;

import com.g12.service.MusicResource;
import com.g12.result.Result;
import io.micrometer.common.util.StringUtils;
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
            int count = musicResource.batchDeleteResources(idsArray);
            if (count != idsArray.size()){
                return Result.success("成功删除" + count + "条数据" + ", 但有" + (idsArray.size()-count) + "条数据不在数据库中");
            }else {
                return Result.success("成功删除" + count + "条数据");
            }
        } catch (Exception e) {
            return Result.error("删除操作失败");
        }
    }

}
