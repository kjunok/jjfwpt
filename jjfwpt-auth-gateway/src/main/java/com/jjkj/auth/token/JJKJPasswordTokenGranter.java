package com.jjkj.auth.token;

import com.jjkj.auth.service.JJKJUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class JJKJPasswordTokenGranter extends JJKJAbstractTokenGranter {

    protected JJKJUserDetailsService userDetailsService;

    public JJKJPasswordTokenGranter(JJKJUserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, "pwd");
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected UserDetails getUser(Map<String, String> parameters) {
        String account = parameters.get("account");
        String password = parameters.get("password");
        return userDetailsService.loadUserByAccount(account,password);
    }
}