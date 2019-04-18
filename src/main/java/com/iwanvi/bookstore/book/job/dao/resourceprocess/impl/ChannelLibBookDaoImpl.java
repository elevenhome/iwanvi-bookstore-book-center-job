package com.iwanvi.bookstore.book.job.dao.resourceprocess.impl;

import com.iwanvi.bookstore.book.job.dao.resourceprocess.ChannelLibBookDao;
import com.iwanvi.bookstore.book.job.dao.resourceprocess.entity.ChanLibBook;
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
 * 渠道图书
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
@Repository
public class ChannelLibBookDaoImpl implements ChannelLibBookDao {

    /**日志**/
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelLibBookDaoImpl.class);
    /**数据源**/
    @Autowired
    @Qualifier("resourceProcessJdbcTemplate")
    private JdbcTemplate resourceProcessJdbcTemplate;

    @Override
    public List<ChanLibBook> getBookCnIds(String bookId, int size) {
        String sql = getSelectSql();
        Object[] args = {bookId, size};
        //LOGGER.info(JSONObject.toJSONString(args));
        return resourceProcessJdbcTemplate.query(sql, args, new ChanLibBookRowMapper());
    }


    @Override
    public List<Map> getBookCnIdsMap(String bookId, int size) {
        String sql = getSelectSql();
        Object[] args = {bookId, size};
        //LOGGER.info(JSONObject.toJSONString(args));
        return resourceProcessJdbcTemplate.query(sql, args, new ChanLibBookRowMapperMap());
    }


    /**
     * 数据库记录转化为实体
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private static class ChanLibBookRowMapper implements RowMapper<ChanLibBook> {
        @Override
        public ChanLibBook mapRow(ResultSet rs, int i) throws SQLException {
            ChanLibBook obj = new ChanLibBook();
            obj.setBookId(rs.getString("bookId"));
            obj.setCnids(rs.getString("cnids"));
            return obj;
        }
    }

    /**
     * 数据库记录转化为实体
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private static class ChanLibBookRowMapperMap implements RowMapper<Map> {
        @Override
        public Map mapRow(ResultSet rs, int i) throws SQLException {
            Map map = new HashMap();
            map.put("bookId", rs.getString("bookId"));
            map.put("cnids", rs.getString("cnids"));
            return map;
        }
    }

    /**
     * 获取查询SQL
     * @return String
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private String getSelectSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT bookId,GROUP_CONCAT(channelId) cnids FROM channel_lib_book")
                .append(" where bookId > ? ")
                .append(" GROUP BY bookId order by bookId  limit ?");
        //LOGGER.info(sql.toString());
        return sql.toString();
    }


}
