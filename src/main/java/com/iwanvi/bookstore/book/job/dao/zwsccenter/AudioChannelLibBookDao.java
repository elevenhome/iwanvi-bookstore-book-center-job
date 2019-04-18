package com.iwanvi.bookstore.book.job.dao.zwsccenter;

import java.util.List;
import java.util.Map;

/**
 * 图书渠道
 * @author zzw
 * @since 2019年3月28日14:39:21
 */
public interface AudioChannelLibBookDao {

    /**
     * 批量查询图书渠道信息
     * @param bookId
     * @param size
     * @return
     */
    List<Map> getBookCnIdsMap(String bookId, int size);
}

