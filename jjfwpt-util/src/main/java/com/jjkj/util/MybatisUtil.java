package com.jjkj.util;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

public class MybatisUtil {
    public final static String regex = "'|%|--|and|or|not|use|insert|delete|update|select|count|group|union" +
            "|create|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|source|sql";
    public static Page getPage(Map<String,Object> condition){
        int start=0;
        int size=15;
        if(condition.containsKey("start")&& !StringUtils.isEmpty(condition.get("start")+"")){
            start=Integer.parseInt(condition.get("start")+"");
        }
        if(condition.containsKey("size")&& !StringUtils.isEmpty(condition.get("size")+"")){
            size=Integer.parseInt(condition.get("size")+"");
        }
        Page page=new Page(start,size);
        condition.forEach((k,v)->{
            if(k.startsWith("sort_")){
                String field=k.replace("sort_","");
                if(field.length()>0){
                    if(v.toString().toUpperCase().equals("ASC")){
                        page.setAsc(field);
                    }else if(v.toString().toUpperCase().equals("DESC")){
                        page.setDesc(field);
                    }
                }
            }
        });
        return page;
    }
    public static QueryWrapper getQueryWrapper(Map<String,Object> condition){
        if(condition.containsKey("start")){
            condition.remove("start");
        }
        if(condition.containsKey("size")){
            condition.remove("size");
        }
        if(condition.containsKey("keyword")){
            condition.remove("keyword");
        }
        if(condition.containsKey("field")){
            condition.remove("field");
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        condition.forEach((k,v)->{
            if(!k.startsWith("sort_")){
                queryWrapper.eq(k,v);
            }
        });
        return queryWrapper;
    }
    public static Map getConditionMap(final Map<String,Object> condition){

       final Map newCondition=new HashMap();
        condition.forEach((p,v)->{
            if(p.indexOf(" ")>-1||p.indexOf("'")>-1){
                newCondition.put(p.replaceAll(" |'",""),v);
            }else {
                if(!("start").equals(p)
                &&!("size").equals(p)
                &&!("keyword").equals(p)
                &&!("field").equals(p)
                &&!("orderMap").equals(p))
                newCondition.put(p,v);
            }
        });
        newCondition.forEach((k,v)->{
            System.out.println(k+" :: "+v);
        });
        return newCondition;
    }

    public static Map<String,Object> getOrderMap(Map<String,Object> condition){
        final Map orderMap=new HashMap();
        if(condition.containsKey("orderMap")){
            ((Map) condition.get("orderMap")).forEach((p,v)->{
                String column=camelToUnderline(p.toString());
                if(column.lastIndexOf("_text")>-1){
                    orderMap.put(column.replace("_text",""),v);
                }else {
                    orderMap.put(column,v);
                }
            });
        }
        return orderMap;
    }
    public static Map<String,Object> getCamelOrderMap(Map<String,Object> condition){
        final Map orderMap=new HashMap();
        ((Map) condition.get("orderMap")).forEach((p,v)->{
            String column=camelToUnderline(p.toString());
            if(column.lastIndexOf("Text")>-1){
                orderMap.put(column.replace("Text",""),v);
            }else {
                orderMap.put(column,v);
            }
        });
        return orderMap;
    }
    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
