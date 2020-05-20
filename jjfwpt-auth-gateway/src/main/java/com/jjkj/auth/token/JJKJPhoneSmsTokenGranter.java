package com.jjkj.auth.token;

import com.jjkj.auth.service.JJKJUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class JJKJPhoneSmsTokenGranter extends JJKJAbstractTokenGranter {

    protected JJKJUserDetailsService userDetailsService;

    public JJKJPhoneSmsTokenGranter(JJKJUserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, "phone_sms");
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected UserDetails getUser(Map<String, String> parameters) {
        String phone = parameters.get("phone");
        String smscode = parameters.get("smscode");
        return userDetailsService.loadUserByPhoneAndSmsCode(phone,smscode);
    }
}