
package com.iwanvi.bookstore.book.job.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.iwanvi.bookstore.book.job.common.BaseResponse;
import com.iwanvi.bookstore.sc.common.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 异常统一处理
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	 /** 日志 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 添加全局异常处理流程，根据需要设置需要处理的异常
	 * @param request
	 * @param response
	 * @param ex
	 * @author zzw
	 * @since 2018年12月14日16:57:47
	 */
	@ExceptionHandler(value = Exception.class)
    public Object myHandler(final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
		LOGGER.error("&系统异常&", ex);
        return new BaseResponse(ResultEnum.FAIL.getRetCode(), "系统繁忙");
    }


}
