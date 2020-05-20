package com.jjkj.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.jjkj.support.http.HttpCode;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class  ResponseDto<T> implements Serializable {

    private long code;
    private T data;
    private String msg;
    private boolean success;
    private Long timestamp = System.currentTimeMillis();


    public ResponseDto success(T data){
        this.code=HttpCode.OK.value();
        this.success=true;
        this.data=data;
        return this;
    }

    public ResponseDto error(String msg){
        this.code=HttpCode.ERROR.value();
        this.success=false;
        this.msg=msg;
        return this;
    }
}
