package com.jjkj.exception;


import com.jjkj.support.http.HttpCode;

/**
 * @author WangJun
 * @version 2016年5月20日 下午3:19:19
 */
@SuppressWarnings("serial")
public class DataParseException extends BaseException {

	public DataParseException() {
	}

	public DataParseException(Throwable ex) {
		super(ex);
	}

	public DataParseException(String message) {
		super(message);
	}

	public DataParseException(String message, Throwable ex) {
		super(message, ex);
	}

	@Override
    protected HttpCode getCode() {
		return HttpCode.INTERNAL_SERVER_ERROR;
	}

}
