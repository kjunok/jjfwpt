package com.jjkj.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjkj.annotation.JJKJAPI;
import com.jjkj.annotation.JJKJParamDesc;
import com.jjkj.api.APIOpenService;
import com.jjkj.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@RestController
public class OpenServiceImpl implements APIOpenService {
    @Override
    public ResponseDto getAllApi() {
        String defaultClassPath = com.jjkj.api.APIOpenService.class.getResource("/com/jjkj/api").getPath();
        File file =  new File(defaultClassPath);
        JSONArray apiList = new JSONArray();
        if(file.isDirectory()){
            File[] files =file.listFiles();
            for (File f1 : files) {
                try {
                    if(f1.getName().lastIndexOf(".java")>-1){
                        continue;
                    }
                    Class<?> clazz = Class.forName("com.jjkj.api" + "."+ f1.getName().substring(0,f1.getName().lastIndexOf(".")));
                    Method[] methods= clazz.getDeclaredMethods();
                    RequestMapping rootRequestMapping= clazz.getDeclaredAnnotation(RequestMapping.class);
                    if(rootRequestMapping!=null){
                        String rootMapping = rootRequestMapping.value()[0];
                        for(Method m:methods){
                            JJKJAPI jjkjapi = m.getDeclaredAnnotation(JJKJAPI.class);
                            if(jjkjapi!=null){
                                JSONObject api = new JSONObject();
                                JSONArray apiParams = new JSONArray();
                                RequestMapping requestMapping= m.getDeclaredAnnotation(RequestMapping.class);
                                api.put("path",rootMapping+requestMapping.value()[0]);
                                api.put("name",jjkjapi.name());
                                api.put("desc",jjkjapi.desc());
                                api.put("response",JSONObject.parse(jjkjapi.response()));
                                Class<?>[] pt= m.getParameterTypes();
                                Annotation[][] p = m.getParameterAnnotations();
                                for(int i=0,len=pt.length;i<len;i++){
                                    JJKJParamDesc jjkjParamDesc = (JJKJParamDesc) p[i][0];
                                    Class type = pt[i];
                                    JSONObject apiParam = new JSONObject();
                                    apiParam.put("name",jjkjParamDesc.name());
                                    apiParam.put("type",type.getSimpleName().toUpperCase());
                                    apiParam.put("desc",jjkjParamDesc.desc());
                                    apiParams.add(apiParam);
                                }
                                api.put("params",apiParams);
                                apiList.add(api);
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return new ResponseDto().error("数据不存在！");
                }
            }
        }
        return new ResponseDto().success(apiList);
    }
}
