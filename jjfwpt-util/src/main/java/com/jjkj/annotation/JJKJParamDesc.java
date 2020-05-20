package com.jjkj.annotation;


import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JJKJParamDesc {
    String name() default "@jjkj_api_desc_请添加描述";

    String desc()  default "@jjkj_api_desc_请添加描述";
}
