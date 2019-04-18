package com.iwanvi.bookstore.book.job.dao.zwsccenter;

import java.util.List;
import java.util.Map;

/**
 * AudioBookDao
 * @author zzw
 * @since 2019年3月28日14:24:00
 */
public interface AudioBookDao {

    /**
     * 查询图书
     * @param id
     * @param size
     * @return
     */
    List<Map> queryBookForSearchForMap(int id, int size);
}
