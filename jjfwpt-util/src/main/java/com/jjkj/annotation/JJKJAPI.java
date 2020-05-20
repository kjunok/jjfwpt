package com.jjkj.annotation;

import com.alibaba.fastjson.JSONObject;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface JJKJAPI {
    String name()  default "@jjkj_api_请添加名称";
    String desc() default "@jjkj_api_请添加描述";
    String response() default "@jjkj_api_请添加返回题";
}
