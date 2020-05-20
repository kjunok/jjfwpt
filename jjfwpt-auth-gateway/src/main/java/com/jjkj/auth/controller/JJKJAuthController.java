package com.jjkj.auth.controller;


import com.alibaba.fastjson.JSONObject;

import com.jjkj.dto.ResponseDto;
import com.jjkj.exception.LoginException;
import com.jjkj.model.TUserInfo;
import com.jjkj.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
*  用户信息控制器
*/
@Service
@Configuration
@RestController
@RequestMapping("/oauth")
public  class JJKJAuthController {

    @Value("${login.authTokenUrl:http://127.0.0.1:10000/oauth/token?client_id=android&client_secret=android}")
    private String loginAuthTokenUrl;


    @Autowired
    private ConsumerTokenServices consumerTokenServices;
    /**
     * 获取授权用户的信息
     * @param user 当前用户
     * @return 授权信息
     */
    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }


    @RequestMapping("/loginByPwd")
    public ResponseDto loginByPwd(@RequestBody TUserInfo tUserInfo){
        StringBuffer authTokenUrl=new StringBuffer();
        authTokenUrl.append(loginAuthTokenUrl)
                    .append("&grant_type=pwd")
                    .append("&account=").append(tUserInfo.getAccount())
                    .append("&password=").append(tUserInfo.getPassword());
       JSONObject response = JSONObject.parseObject(HttpUtil.post(authTokenUrl.toString()));
       if(response.containsKey("error_description")){
           throw new LoginException(response.getString("error_description"));
       }
       return new ResponseDto().success(response);
    }


    @RequestMapping("/logOut")
    public ResponseDto logout(@RequestHeader("Authorization") String authorization){
          consumerTokenServices.revokeToken(authorization.replace("Bearer ",""));
          return new ResponseDto().success("注销成功！");
    }
}