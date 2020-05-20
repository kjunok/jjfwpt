package com.jjkj.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JJKJUserDetailsService extends UserDetailsService {
    public UserDetails loadUserByAccount(String account, String password);
    public UserDetails loadUserByPhoneAndSmsCode(String phone, String smsCode);
}
