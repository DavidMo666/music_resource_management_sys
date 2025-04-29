package com.g12.mapper;

import com.g12.dto.AdminLoginDTO;
import com.g12.entity.Admin;
import com.g12.entity.User;
import com.g12.vo.DailyNewMusicVO;
import com.g12.vo.DailyUserCountVO;
import com.g12.vo.TagDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("SELECT id, username, password FROM music_resource_system.admin WHERE username = #{username}")
    Admin login(AdminLoginDTO adminLoginDTO);

    /**
     * 统计用户总数
     */
    @Select("SELECT COUNT(id) FROM music_resource_system.user")
    Long countUsers();

    /**
     * 统计音乐资源总数
     */
    @Select("SELECT COUNT(id) FROM music_resource_system.music_resources")
    Long countMusicResources();

    @Select("SELECT \n" +
            "    t.name AS tag_name, \n" +
            "    COUNT(DISTINCT mt.music_id) AS music_count \n" +
            "FROM \n" +
            "    tag t \n" +
            "LEFT JOIN \n" +
            "    music_tag mt ON t.id = mt.tag_id \n" +
            "GROUP BY \n" +
            "    t.id, t.name;")
    List<TagDataVO> countMusicResourcesByTag();

    /**
     * 当天的新用户
     * @return
     */
    @Select(" WITH RECURSIVE dates(date) AS (\n" +
            "        SELECT DATE_SUB(CURDATE(), INTERVAL 0 DAY)  -- 起始日期为29天前\n" +
            "        UNION ALL\n" +
            "        SELECT DATE_ADD(date, INTERVAL 1 DAY)\n" +
            "        FROM dates\n" +
            "        WHERE date < CURDATE()  -- 生成到当前日期\n" +
            "    )\n" +
            "    SELECT \n" +
            "        dates.date AS date,\n" +
            "        IFNULL(COUNT(user.id), 0) AS count  -- 无用户时填充0\n" +
            "    FROM dates\n" +
            "    LEFT JOIN user ON DATE(user.create_time) = dates.date\n" +
            "    GROUP BY dates.date\n" +
            "    ORDER BY dates.date DESC;")
    List<DailyUserCountVO> countDailyNewUsers();

    @Select("WITH RECURSIVE dates(date) AS (\n" +
            "    SELECT DATE_SUB(CURDATE(), INTERVAL 29 DAY)  -- 生成起始日期（30天前）\n" +
            "    UNION ALL\n" +
            "    SELECT DATE_ADD(date, INTERVAL 1 DAY)         -- 递归生成后续日期\n" +
            "    FROM dates\n" +
            "    WHERE date < CURDATE()                        -- 终止条件：生成到当前日期\n" +
            ")\n" +
            "SELECT \n" +
            "    dates.date AS date,\n" +
            "    (\n" +
            "        SELECT COUNT(*) \n" +
            "        FROM user \n" +
            "        WHERE DATE(create_time) <= dates.date    -- 统计截至该日期的所有用户\n" +
            "    ) AS count\n" +
            "FROM dates\n" +
            "ORDER BY dates.date DESC;                        -- 按日期降序排列")
    List<DailyUserCountVO> countDailyTotalUsers();

    @Select("SELECT \n" +
            "    CURDATE() AS date,  -- 直接返回今日日期（固定值）\n" +
            "    COUNT(*) AS count   -- 统计符合条件的记录总数\n" +
            "FROM music_resources \n" +
            "WHERE \n" +
            "    upload_time >= CURDATE() \n" +
            "    AND upload_time < CURDATE() + INTERVAL 1 DAY;")
    List<DailyNewMusicVO> countDailyNewMusic();

    @Select("SELECT COUNT(id) FROM music_resource_system.user WHERE status = 0")
    Integer countBlockUsers();

    @Select("SELECT count(id) FROM music_resource_system.music_resources WHERE status = 0;")
    Integer countBlockMusics();
}
