package com.g12.controller.admin;

import com.g12.dto.MusicResourcePageQueryDTO;
import com.g12.dto.UserPageQueryDTO;
import com.g12.result.PageResult;
import com.g12.result.Result;
import com.g12.service.MusicResourceService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/resource")
public class MusicResourceController {

    @Autowired
    MusicResourceService musicResourceService;

    @GetMapping("/page")
    public Result<PageResult> pageQuery(MusicResourcePageQueryDTO musicResourcePageQueryDTO){

        log.info("音乐资源分页查询:{}", musicResourcePageQueryDTO);

        PageResult pageResult = musicResourceService.pageQuery(musicResourcePageQueryDTO);

        return Result.success(pageResult);
    }

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
     * 更新音乐资源状态（封禁/解封）
     * @param status 状态（0-封禁，1-正常）
     * @param id 音乐资源ID
     * @return 操作结果
     */
    @PutMapping("/status/{status}")
    public Result updateStatus(
            @PathVariable(value = "status") Integer status,
            @RequestParam("id") Integer id) {

//        log.info("开始更新音乐资源状态：status={}, id={}", status, id);

        // 参数校验
        if (id == null) {
            return Result.error("参数id不能为空");
        }

        if (status != 0 && status != 1) {
            return Result.error("状态参数无效，只能为0（封禁）或1（正常）");
        }

        try {
            boolean success = musicResourceService.updateStatus(status, id);
            if (success) {
                String statusText = status == 0 ? "封禁" : "解封";
                return Result.success("音乐资源" + statusText + "成功");
            } else {
                return Result.error("音乐资源不存在或状态未变更");
            }
        } catch (Exception e) {
//            log.error("更新音乐资源状态失败", e);  // 打印完整的异常堆栈
            return Result.error("操作失败，请稍后重试");
        }
    }
    /**
     * 查询音乐资源（支持按用户ID、名称或组合查询）
     * @param userId 用户ID（可选）
     * @param name 音乐名称（可选）
     * @return 音乐资源列表
     */
    @GetMapping("/list")
    public Result listMusicResource(
            @RequestParam(value = "userid", required = false) Integer userId,
            @RequestParam(value = "name", required = false) String name) {

        // 参数校验
        if (userId == null && name == null) {
            return Result.error("至少需要提供一个查询参数(userid或name)");
        }

        try {
            PageResult pageResult = musicResourceService.listByCondition(userId, name);

            // 判断是否查询到数据
            if (pageResult.getTotal() == 0) {
                return Result.error("未找到符合条件的音乐资源");
            }

            return Result.success(pageResult);
        } catch (Exception e) {
//            log.error("查询音乐资源失败", e);
            return Result.error("系统繁忙，请稍后重试");
        }
    }




}
