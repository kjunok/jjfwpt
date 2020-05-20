package com.jjkj.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;


@Component
public class JJKJSecurityWhitelistHandler {

	@Value("${white-resources}")
    private String[] whiteResources={};

	public HttpSecurity handle(HttpSecurity http) throws Exception {

		return http
				.authorizeRequests()
				.antMatchers(whiteResources)
				.permitAll()
				.and();
	}
}