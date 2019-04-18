package com.iwanvi.bookstore.book.job.dao.resourceprocess.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * 图书信息
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public class ESBook implements Serializable {
    private Integer id;
    private String name;
    private String imgUrl;
    private String intro;
    private String cat2;
    private String cat3;
    private String author;
    private String keyword;
    private String wordCount;
    private String bookId;
    private String price;
    private Date bookUpdateTime;
    private Date lastUpdateChapterDate;
    private String serial;
    private String feeType;
    private String isFree;
    private String cat2Name;
    private String cat3Name;
    private String copyright;
    private String clickCount;
    private String saleCount;
    private String readCount;

    private Long udapteTime;
    private String[] cnids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getBookUpdateTime() {
        return bookUpdateTime;
    }

    public void setBookUpdateTime(Date bookUpdateTime) {
        this.bookUpdateTime = bookUpdateTime;
    }

    public Date getLastUpdateChapterDate() {
        return lastUpdateChapterDate;
    }

    public void setLastUpdateChapterDate(Date lastUpdateChapterDate) {
        this.lastUpdateChapterDate = lastUpdateChapterDate;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getCat2Name() {
        return cat2Name;
    }

    public void setCat2Name(String cat2Name) {
        this.cat2Name = cat2Name;
    }

    public String getCat3Name() {
        return cat3Name;
    }

    public void setCat3Name(String cat3Name) {
        this.cat3Name = cat3Name;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    public String getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(String saleCount) {
        this.saleCount = saleCount;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public Long getUdapteTime() {
        return udapteTime;
    }

    public void setUdapteTime(Long udapteTime) {
        this.udapteTime = udapteTime;
    }

    public String[] getCnids() {
        return cnids;
    }

    public void setCnids(String[] cnids) {
        this.cnids = cnids;
    }
}
