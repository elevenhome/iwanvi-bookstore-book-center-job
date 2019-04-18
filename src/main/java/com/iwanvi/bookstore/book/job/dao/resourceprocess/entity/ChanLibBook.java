package com.iwanvi.bookstore.book.job.dao.resourceprocess.entity;

import java.io.Serializable;

/**
 * 渠道图书
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public class ChanLibBook implements Serializable {
    private String bookId;
    private String cnids;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCnids() {
        return cnids;
    }

    public void setCnids(String cnids) {
        this.cnids = cnids;
    }
}
