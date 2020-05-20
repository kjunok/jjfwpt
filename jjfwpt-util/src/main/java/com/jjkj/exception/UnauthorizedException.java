package com.jjkj.exception;


import com.jjkj.support.http.HttpCode;

/**
 * @author WangJun
 * @since 2019年4月4日 下午2:59:14
 */
@SuppressWarnings("serial")
public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Exception e) {
        super(message, e);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.UNAUTHORIZED;
    }

}
