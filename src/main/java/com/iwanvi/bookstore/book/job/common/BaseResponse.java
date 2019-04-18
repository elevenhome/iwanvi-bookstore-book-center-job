package com.iwanvi.bookstore.book.job.common;

import com.iwanvi.bookstore.sc.common.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 响应实体
 * @author zzw
 * @since 2018年12月14日16:54:14
 */
@ApiModel(value = "响应实体", description = "JsonResult")
public class BaseResponse<T> extends  BaseEntity{

	@ApiModelProperty(value="响应码，如：成功：0000， 失败：9999")
	public String retCode;

	@ApiModelProperty(value="响应信息，如：成功")
	public String retInfo;

	@ApiModelProperty(value="返回结果数据")
	public T result;

	public BaseResponse() {
	}

	public BaseResponse(String retCode,String retInfo) {
		this.retCode = retCode;
		this.retInfo = retInfo;
	}

	public BaseResponse(String retCode, String retInfo, T result) {
		this.retCode = retCode;
		this.retInfo = retInfo;
		this.result = result;
	}

	public BaseResponse(ResultEnum resultEnum, T result) {
		this.retCode = resultEnum.getRetCode();
		this.retInfo = resultEnum.getRetInfo();
		this.result = result;
	}

	public BaseResponse(ResultEnum resultEnum) {
		this.retCode = resultEnum.getRetCode();
		this.retInfo = resultEnum.getRetInfo();
	}

	public void setResultEnum(ResultEnum resultEnum) {
		this.retCode = resultEnum.getRetCode();
		this.retInfo = resultEnum.getRetInfo();

	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetInfo() {
		return retInfo;
	}

	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
