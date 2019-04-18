package com.iwanvi.bookstore.book.job.common.exception;

/**
 * Service异常
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public class ServiceException extends Exception {

	public ServiceException() {
		super();
	}

	public ServiceException(final String message) {
		super(message);
	}

	public ServiceException(final Throwable cause) {
		super(cause);
	}

	public ServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ServiceException(final String message, final Throwable cause, final boolean enableSuppression,
							final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
