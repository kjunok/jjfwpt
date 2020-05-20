package com.jjkj.util;

import com.alibaba.fastjson.JSONObject;

public class SessionUtil {
    public static Long getUserID(String token){
       JSONObject object = JSONObject.parseObject(AESUtil.decrypt(token));
       return object.getLong("b");
    }
    public static String getUserIDstr(String token){
        JSONObject object = JSONObject.parseObject(AESUtil.decrypt(token));
        return object.getString("b");
    }
}
