/**
 * 不合法参数异常
 */
package com.jjkj.exception;


import com.jjkj.support.http.HttpCode;

/**
 * 
 * @author WangJun
 * @version 2016年6月7日 下午8:46:11
 */
@SuppressWarnings("serial")
public class IllegalParameterException extends BaseException {
	public IllegalParameterException() {
	}

	public IllegalParameterException(Throwable ex) {
		super(ex);
	}

	public IllegalParameterException(String message) {
		super(message);
	}

	public IllegalParameterException(String message, Throwable ex) {
		super(message, ex);
	}

	@Override
    protected HttpCode getCode() {
		return HttpCode.BAD_REQUEST;
	}
}
