package com.iwanvi.bookstore.book.job.dao.resourceprocess;

import com.iwanvi.bookstore.book.job.dao.resourceprocess.entity.ChanLibBook;

import java.util.List;
import java.util.Map;

/**
 * 渠道图书
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public interface ChannelLibBookDao {

    List<ChanLibBook>  getBookCnIds(String bid, int size);

    List<Map>  getBookCnIdsMap(String bid, int size);
}
