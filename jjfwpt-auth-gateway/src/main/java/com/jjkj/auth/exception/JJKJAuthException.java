package com.jjkj.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = JJKJAuthExceptionSerializer.class)
public class JJKJAuthException extends OAuth2Exception {
    private Integer status = 400;

    public JJKJAuthException(String message, Throwable t) {
        super(message, t);
        status = ((OAuth2Exception)t).getHttpErrorCode();
    }

    public JJKJAuthException(String message) {
        super(message);
    }
    @Override
    public int getHttpErrorCode() {
        return status;
    }
}