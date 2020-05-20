package com.jjkj.auth.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.jjkj.auth.service.JJKJUserDetailsService;
import com.jjkj.model.TUserInfo;
import com.jjkj.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

/**
* 用户信息服务
* 实现 Spring Security的UserDetailsService接口方法，用于身份认证
*/
@Service
public class JJKJUserDetailsServiceImpl implements JJKJUserDetailsService {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 根据用户名查找账户信息并返回用户信息实体
     * @param account 用户登录名
     * @return 用于身份认证的 UserDetails 用户信息实体
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        TUserInfo userInfo = userService.queryByAccount(account);

        if (userInfo!=null){
            return userInfo;
        }else {
            throw  new UsernameNotFoundException("用户["+account+"]不存在");
        }
    }

    @Override
    public UserDetails loadUserByAccount(String account, String password) {
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            throw new InvalidGrantException("账号或密码不能为空");
        }
        TUserInfo userInfo = userService.queryByAccount(account);
        if (userInfo!=null){
           if(PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(password,userInfo.getPassword())){
               return userInfo;
           }else {
               throw  new UsernameNotFoundException("账号或密码错误");
           }
        }else {
            throw  new UsernameNotFoundException("账号不存在");
        }
    }

    @Override
    public UserDetails loadUserByPhoneAndSmsCode(String phone, String smsCode) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsCode)) {
            throw new InvalidGrantException("无效的手机号或短信验证码");
        }
        // 判断成功后返回用户细节
        return new User(phone, "", AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user,root"));
    }

    public static void main(String[] args) {
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("e10adc3949ba59abbe56e057f20f883e"));
     boolean s =   PasswordEncoderFactories.createDelegatingPasswordEncoder().matches("1234563","{bcrypt}$2a$10$SE96WpCh.cDjC0C3dIsv0OLtJxlSVgMPnAPHHYG6e7/GQD.WQvcKa");
        System.out.println(s);
    }
}