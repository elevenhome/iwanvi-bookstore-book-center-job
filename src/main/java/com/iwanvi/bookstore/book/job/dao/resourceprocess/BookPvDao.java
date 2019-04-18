package com.iwanvi.bookstore.book.job.dao.resourceprocess;

import com.iwanvi.bookstore.book.job.dao.common.BaseDao;



/**
 * book_pv DAO
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public interface BookPvDao extends BaseDao {

    String queryMaxDt();

    long querySortReadPvByDtAndNum(String dt, int num);
}
