package com.iwanvi.bookstore.book.job.dao.resourceprocess;


import com.iwanvi.bookstore.book.job.dao.resourceprocess.entity.ESBook;

import java.util.List;
import java.util.Map;

/**
 * 图书接口
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public interface BookDao {

    /**
     * 查询图书信息
     * @Parm id book_v3 主键
     * @Parm size 返回的记录数
     * @Parm dt book_pv 最新日期
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    List<ESBook> queryBookForSearch(int id, int size, String dt);

    List<Map> queryBookForSearchForMap(int id, int size, String dt);
}
