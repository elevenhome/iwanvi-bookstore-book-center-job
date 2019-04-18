package com.iwanvi.bookstore.book.job.dao.zwsccenter.impl;

import com.iwanvi.bookstore.book.job.dao.common.BaseDaoImpl;
import com.iwanvi.bookstore.book.job.utils.Utils;
import com.iwanvi.bookstore.sc.common.model.zwsc_center.NomalBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 无声图书信息
 * @author zzw
 * @since 2019年4月16日16:58:12
 */
@Repository
public class NomalBookDaoImpl extends BaseDaoImpl {
    /**日志**/
    private static final Logger LOGGER = LoggerFactory.getLogger(NomalBookDaoImpl.class);
    /**无声图书表**/
    private static final String TABLE_NAME = "nomal_book";
    /**数据库连接**/
    @Autowired
    @Qualifier("zwscCenterJdbcTemplate")
    private JdbcTemplate resourceProcess;

    /**
     * 根据图书ID查询(不检查是否下架状态)
     * @param bookId 图书ID
     * @return Book 图书信息
     * @author zzw
     * @since 2018年8月11日14:26:28
     */
    public NomalBook getBookByBookIdNoCheck(final String bookId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * FROM ").append(TABLE_NAME).append(" WHERE zc_id =?");
        Object[] args = {bookId};
        List<NomalBook> list = resourceProcess.query(sql.toString(), args, new BookV3RowMapper());
        if (Utils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据图书ID查询（只查询上架的图书）
     * @param bookId 图书ID
     * @return Book  图书信息
     * @author zzw
     * @since 2018年8月11日14:26:28
     */
    public NomalBook getBookByBookId(final String bookId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * FROM ").append(TABLE_NAME).append(" WHERE zc_id = ? AND status > 0");
        Object[] args = {bookId};
        List<NomalBook> list = resourceProcess.query(sql.toString(), args, new BookV3RowMapper());
        if (Utils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }



    /**
     * 分装返回实体
     */
    private static class BookV3RowMapper implements RowMapper<NomalBook> {
        @Override
        public NomalBook mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            NomalBook obj = new NomalBook();
            obj.setId(rs.getLong("id"));
            obj.setZcId(rs.getString("zc_id"));
            obj.setBookName(rs.getString("book_name"));
            obj.setNewBookName(rs.getString("new_book_name"));
            obj.setImgUrl(rs.getString("img_url"));
            obj.setIntroduction(rs.getString("introduction"));
            obj.setWordCount(rs.getInt("word_count"));
            obj.setCategoryId(rs.getInt("category_id"));
            obj.setCategorySec(rs.getInt("category_sec"));
            obj.setCategoryThr(rs.getInt("category_thr"));
            obj.setAuthorPenname(rs.getString("author_penname"));
            obj.setKeyWord(rs.getString("keyWord"));
            obj.setPublish(rs.getString("publish"));
            obj.setHaveVolume(rs.getInt("haveVolume"));
            obj.setStatus(rs.getInt("status"));
            obj.setBookPrice(rs.getFloat("bookPrice"));
            obj.setCreateDate(rs.getTimestamp("create_date"));
            obj.setUpdateDate(rs.getTimestamp("update_date"));
            obj.setCopyRightTime(rs.getTimestamp("copyRightTime"));
            obj.setLastUpdateChapterDate(rs.getTimestamp("lastUpdateChapterDate"));
            obj.setLastUpdateChapterName(rs.getString("lastUpdateChapterName"));
            obj.setLastUpdateChapterId(rs.getLong("lastUpdateChapterId"));
            obj.setBookStatue(rs.getString("bookStatue"));
            obj.setPindaoId(rs.getInt("pindaoId"));
            return obj;
        }
    }


}
