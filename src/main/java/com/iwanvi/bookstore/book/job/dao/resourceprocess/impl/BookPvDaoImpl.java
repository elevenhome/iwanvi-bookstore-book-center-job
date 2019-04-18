package com.iwanvi.bookstore.book.job.dao.resourceprocess.impl;

import com.iwanvi.bookstore.sc.common.model.resource_process.BookPv;
import com.iwanvi.bookstore.book.job.dao.common.BaseDaoImpl;
import com.iwanvi.bookstore.book.job.dao.resourceprocess.BookPvDao;
import com.iwanvi.bookstore.book.job.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * book_pv DAO
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
@Repository
public class BookPvDaoImpl extends BaseDaoImpl implements BookPvDao {

    /**表名**/
    private static final String TABLE_NAME = "book_pv";

    @Autowired
    @Qualifier("resourceProcessJdbcTemplate")
    private JdbcTemplate resourceProcessJdbcTemplate;

    @Override
    public String queryMaxDt() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT dt FROM ").append(TABLE_NAME)
                .append(" ORDER BY id DESC LIMIT 1");
        List<String> list = resourceProcessJdbcTemplate.query(sql.toString(), new RowMapper<String>() {
           public String mapRow(ResultSet rs, int rowNum) throws SQLException {
               return rs.getString("dt");
           }
        });
        if (Utils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public long querySortReadPvByDtAndNum(String dt, int num) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ifnull(min(read_pv),0) read_pv FROM (")
                .append("SELECT read_pv FROM ").append(TABLE_NAME)
                .append(" WHERE dt = ? ORDER BY read_pv DESC LIMIT 0,?")
                .append(") a");
        Object[] args = {dt, num};
        List<Long> list = resourceProcessJdbcTemplate.query(sql.toString(), args, new RowMapper<Long>() {
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("read_pv");
            }
        });
        if (Utils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return 0;
        }
    }


    /**
     * 数据库记录转化为实体
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private static class BookPvRowMapper implements RowMapper<BookPv> {
        @Override
        public BookPv mapRow(ResultSet rs, int i) throws SQLException {
            BookPv obj = new BookPv();
            obj.setId(rs.getLong("id"));
            obj.setBookId(rs.getString("book_id"));
            obj.setClickPv(rs.getLong("click_pv"));
            obj.setSaleCount(rs.getLong("sale_count"));
            obj.setDt(rs.getString("dt"));
            obj.setReadPv(rs.getLong("read_pv"));
            return null;
        }
    }


}
