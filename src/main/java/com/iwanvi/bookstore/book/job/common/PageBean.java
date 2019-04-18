package com.iwanvi.bookstore.book.job.common;

import com.iwanvi.bookstore.book.job.utils.Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页Bean
 * @author zzw
 * @since 2018年12月14日15:00:51
 */
@ApiModel(value = "分页实体")
public class PageBean<T> implements Serializable {

	@ApiModelProperty(value = "当前页，默认第1页")
	private int pageNo = 1;

	@ApiModelProperty(value = "每页显示条数，默认10条")
	private int pageSize = 20;

	@ApiModelProperty(value = "总条数")
	private long total = 0;

	@ApiModelProperty(value = "总页数")
	private int pages = 0;

	@ApiModelProperty(value = "返回数据")
	private List<T> dataList = new ArrayList<>();

	public PageBean(){}

	public PageBean(int pageNo) {
		this.pageNo = (pageNo == 0 ? 1 : pageNo);
	}

	public PageBean(int pageNo, int pageSize, int total, List<T> dataList) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
		if (Utils.isEmpty(dataList)) {
			this.dataList = new ArrayList<>();
		} else {
			this.dataList = dataList;
		}

		this.pages = getPages(total, pageSize);
	}



	public PageBean(int pageNo, int pageSize, long total, int pages, List<T> dataList) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
		this.pages = pages;
		this.dataList = dataList;
	}

	/**
	 *@Title: PageBean
	 * TODO
	 *@param pageNo
	 *@param pageSize
	 */
	public PageBean(int pageNo, int pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	//总页数
	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPages(int total, int pageSize) {
		int pages = 0;
		if (total > 0) {
			if (total%pageSize==0) {
				pages = total/pageSize;
			} else {
				pages = total/pageSize + 1;
			}
		}
		return pages;
	}

	public void setPages(int total, int pageSize) {
		this.pages = getPages(total,pageSize);
	}
}

