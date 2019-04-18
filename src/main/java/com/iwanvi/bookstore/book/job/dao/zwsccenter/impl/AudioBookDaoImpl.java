package com.iwanvi.bookstore.book.job.dao.zwsccenter.impl;

import com.iwanvi.bookstore.book.job.dao.zwsccenter.AudioBookDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AudioBookDaoImpl
 * @author zzw
 * @since 2019年3月28日14:26:47
 */
@Repository
public class AudioBookDaoImpl implements AudioBookDao {
    /**日志**/
    private static final Logger LOGGER = LoggerFactory.getLogger(AudioBookDaoImpl.class);

    /**数据源**/
    @Autowired
    @Qualifier("zwscCenterJdbcTemplate")
    private JdbcTemplate zwscCenterJdbcTemplate;


    /**
     * 查询图书
     * @param id
     * @param size
     * @return
     * @since 2019年4月10日15:35:27
     */
    public List<Map> queryBookForSearchForMap(int id, int size) {
        String sql = getSelectSql();
        //LOGGER.info("查询图书 sql={}", sql);
        Object[] args = {id, size};
        return zwscCenterJdbcTemplate.query(sql, args, new BookRowMapperMap());
    }


    /**
     * 数据库记录转化为MAP
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private static class BookRowMapperMap implements RowMapper<Map> {
        @Override
        public Map mapRow(ResultSet rs, int i) throws SQLException {
            Map map = new HashMap();
            map.put("id", rs.getLong("id"));
            map.put("bookType", rs.getInt("bookType"));
            map.put("bookId", rs.getString("bookId"));
            map.put("bookName", rs.getString("bookName"));
            map.put("imgUrl", rs.getString("imgUrl"));
            map.put("longImgUrl", rs.getString("longImgUrl"));
            map.put("introduction", rs.getString("introduction"));
            map.put("status", rs.getInt("status"));
            map.put("bookStatus", rs.getInt("bookStatus"));
            map.put("authorName", rs.getString("authorName"));
            map.put("lecturer", rs.getString("lecturer"));
            map.put("lecturerIntroduction", rs.getString("lecturerIntroduction"));
            map.put("createDate", rs.getTimestamp("createDate"));
            map.put("updateDate", rs.getTimestamp("updateDate"));
            map.put("publishTime", rs.getTimestamp("publishTime"));
            map.put("lastUpdateChapterId", rs.getLong("lastUpdateChapterId"));
            map.put("lastUpdateChapterName", rs.getString("lastUpdateChapterName"));
            map.put("lastUpdateChapterDate", rs.getTimestamp("lastUpdateChapterDate"));
            map.put("categoryId", rs.getLong("categoryId"));
            map.put("categoryName", rs.getString("categoryName"));
            return map;
        }
    }

    /**
     * 获取查询SQL
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private String getSelectSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT")
        .append(" audio_book.id  as id,")
        .append(" 1 as bookType,")
        .append(" book_id as bookId,")
        .append(" title as bookName,")
        .append(" img_url as imgUrl,")
        .append(" long_img_url as longImgUrl,")
        .append(" ifnull(introduction,short_introduction) as introduction,")
        .append(" status,")
        .append(" audio_status as bookStatus,")
        .append(" author_name as authorName,")
        .append(" lecturer,")
        .append(" lecturer_introduction as lecturerIntroduction,")
        .append(" keyword,")
        .append(" audio_book.create_date as createDate,")
        .append(" audio_book.update_date as updateDate,")
        .append(" publish_time as publishTime,")
        .append(" last_update_chapter_id as lastUpdateChapterId,")
        .append(" last_update_chapter_name as lastUpdateChapterName,")
        .append(" last_update_chapter_date as lastUpdateChapterDate,")
        .append(" cat.category_id as categoryId,")
        .append(" cat.name as categoryName")
        .append(" FROM audio_book left join audio_category cat")
        .append(" ON audio_book.category_id = cat.id")
        .append(" WHERE audio_book.id  > ? and audio_book.status > 0")
        .append(" ORDER BY audio_book.id")
        .append(" LIMIT ?");
        return sql.toString();
    }

}

