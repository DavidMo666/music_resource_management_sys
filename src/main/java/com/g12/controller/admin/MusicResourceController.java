package com.g12.controller.admin;

import com.g12.service.MusicResource;
import com.g12.result.Result;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    /**
     * 查询音乐资源（支持按用户ID、名称或组合查询）
     * @param userid 用户ID（可选）
     * @param name 音乐名称（可选）
     * @return 音乐资源列表
     */
    @GetMapping("/admin/resource/list")
    public Result listMusicResource(
            @RequestParam(value = "userid", required = false) Integer userId,
            @RequestParam(value = "name", required = false) String name) {
        
        // 如果两个参数都为空，返回错误
        if (userId == null && name == null) {
            return Result.error("至少需要提供一个查询参数(userid或name)");
        }
        
        return Result.success(musicResource.listByCondition(userId, name));
    }
    
}
