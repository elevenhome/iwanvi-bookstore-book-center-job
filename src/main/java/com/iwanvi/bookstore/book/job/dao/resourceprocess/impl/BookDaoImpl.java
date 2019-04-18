package com.iwanvi.bookstore.book.job.dao.resourceprocess.impl;

import com.iwanvi.bookstore.book.job.dao.resourceprocess.BookDao;
import com.iwanvi.bookstore.book.job.dao.resourceprocess.entity.ESBook;
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
 * @Desc
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    @Qualifier("resourceProcessJdbcTemplate")
    protected JdbcTemplate resourceProcessJdbcTemplate;


    @Override
    public List<ESBook> queryBookForSearch(int id, int size, String dt) {
        String sql = getSelectSql();
        Object[] args = {dt, id, size};
        return resourceProcessJdbcTemplate.query(sql, args, new BookRowMapper());
    }

    @Override
    public List<Map> queryBookForSearchForMap(int id, int size, String dt) {
        String sql = getSelectSql();
        Object[] args = {dt, id, size};
        return resourceProcessJdbcTemplate.query(sql, args, new BookRowMapperMap());
    }

    /**
     * 数据库记录转化为实体
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private static class BookRowMapper implements RowMapper<ESBook> {
        @Override
        public ESBook mapRow(ResultSet rs, int i) throws SQLException {
            ESBook obj = new ESBook();
            obj.setId(rs.getInt("id"));
            obj.setName(rs.getString("name"));
            obj.setImgUrl(rs.getString("imgUrl"));
            obj.setIntro(rs.getString("intro"));
            obj.setCat2(rs.getString("cat2"));
            obj.setCat3(rs.getString("cat3"));
            obj.setAuthor(rs.getString("author"));
            obj.setKeyword(rs.getString("keyword"));
            obj.setWordCount(rs.getString("wordCount"));
            obj.setBookId(rs.getString("bookId"));
            obj.setPrice(rs.getString("price"));
            obj.setBookUpdateTime(rs.getTimestamp("bookUpdateTime"));
            obj.setLastUpdateChapterDate(rs.getTimestamp("lastUpdateChapterDate"));
            obj.setSerial(rs.getString("serial"));
            obj.setFeeType(rs.getString("feeType"));
            obj.setIsFree(rs.getString("isFree"));
            obj.setCat2Name(rs.getString("cat2Name"));
            obj.setCat3Name(rs.getString("cat3Name"));
            obj.setCopyright(rs.getString("copyright"));
            obj.setClickCount(rs.getString("clickCount"));
            obj.setSaleCount(rs.getString("saleCount"));
            obj.setReadCount(rs.getString("readCount"));
            return obj;
        }
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
            map.put("id", rs.getInt("id"));
            map.put("name", rs.getString("name"));
            map.put("imgUrl", rs.getString("imgUrl"));
            map.put("intro", rs.getString("intro"));
            map.put("cat2", rs.getString("cat2"));
            map.put("cat3", rs.getString("cat3"));
            map.put("author", rs.getString("author"));
            map.put("keyword", rs.getString("keyword"));
            map.put("wordCount", rs.getString("wordCount"));
            map.put("bookId", rs.getString("bookId"));
            map.put("price", rs.getString("price"));
            map.put("bookUpdateTime", rs.getTimestamp("bookUpdateTime"));
            map.put("lastUpdateChapterDate", rs.getTimestamp("lastUpdateChapterDate"));
            map.put("serial", rs.getString("serial"));
            map.put("feeType", rs.getString("feeType"));
            map.put("isFree", rs.getString("isFree"));
            map.put("cat2Name", rs.getString("cat2Name"));
            map.put("cat3Name", rs.getString("cat3Name"));
            map.put("copyright", rs.getString("copyright"));
            map.put("clickCount", rs.getString("clickCount"));
            map.put("saleCount", rs.getString("saleCount"));
            map.put("readCount", rs.getString("readCount"));
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
                .append(" book_v3.id  as id,")
                .append(" ifnull(new_book_name,book_name) as name,")
                .append(" img_url as imgUrl,")
                .append(" introduction as intro,")
                .append(" category_sec as cat2,")
                .append(" category_thr as cat3,")
                .append(" author_penname as author,")
                .append(" keyWord as keyword,")
                .append(" word_count as wordCount,")
                .append(" zc_id as bookId,")
                .append(" bookPrice as price,")
                .append(" update_date as bookUpdateTime,")
                .append(" lastUpdateChapterDate as lastUpdateChapterDate,")
                .append(" bookStatue as serial,")
                .append(" 1 as feeType,")
                .append(" 1 as isFree,")
                .append(" cat_tab2.name as cat2Name,")
                .append(" cat_tab3.name as cat3Name,")
                .append(" 1 as copyright,")
                .append(" ifnull(bpv.click_pv,0) as clickCount,")
                .append(" ifnull(bpv.sale_count,0) as saleCount,")
                .append(" ifnull(bpv.read_pv,0) as readCount")
                .append(" from book_v3 left join bookcategory_v3 cat_tab2")
                .append(" on book_v3.category_sec = cat_tab2.id")
                .append(" left join bookcategory_v3 cat_tab3")
                .append(" on book_v3.category_thr = cat_tab3.id")
                .append(" left join  book_pv  bpv")
                .append(" on book_v3.zc_id = bpv.book_id and bpv.dt = ?")
                .append(" where book_v3.id  > ? and status > 0")
                .append(" order by book_v3.id")
                .append(" limit ?");
        return sql.toString();
    }

}
