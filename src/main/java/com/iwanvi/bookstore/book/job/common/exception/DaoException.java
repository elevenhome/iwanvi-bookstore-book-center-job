package com.iwanvi.bookstore.book.job.common.exception;

/**
 * Dao异常
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public class DaoException extends Exception {

	public DaoException() {
		super();
	}

	public DaoException(final String message, final Throwable cause, final boolean enableSuppression,
						final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DaoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DaoException(final String message) {
		super(message);
	}

	public DaoException(final Throwable cause) {
		super(cause);
	}
}
