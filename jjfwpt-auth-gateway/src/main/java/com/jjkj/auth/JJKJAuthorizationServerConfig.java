package com.jjkj.auth;

import com.alibaba.fastjson.JSON;
import com.jjkj.auth.service.JJKJUserDetailsService;
import com.jjkj.auth.token.JJKJRefreshTokenGranter;
import com.jjkj.auth.token.JJKJPasswordTokenGranter;
import com.jjkj.auth.token.JJKJPhoneSmsTokenGranter;
import com.jjkj.auth.token.JJKJTokenConverter;
import com.jjkj.model.TUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* 授权服务器配置
* @ EnableAuthorizationServer 启用授权服务
*/
@Configuration
@EnableAuthorizationServer
public class JJKJAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;    // 认证管理器

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;  // redis连接工厂

    @Autowired
    private JJKJUserDetailsService userDetailsService;

    /**
     * 令牌存储
     * @return redis令牌存储对象
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
    }

    @Bean
    public JJKJTokenConverter accessTokenConverter() {
        return new JJKJTokenConverter();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        List<TokenGranter> tokenGranters = getTokenGranters(endpoints.getAuthorizationCodeServices(), endpoints.getTokenStore(), endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory());
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));
        endpoints.tokenStore(tokenStore());
        //指定认证管理器
        endpoints.authenticationManager(authenticationManager);
        //指定token存储位置
        endpoints.tokenStore(tokenStore());
        // 自定义token生成方式
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
                TUserInfo user = (TUserInfo) authentication.getPrincipal();
                user.setPassword(null);
                Map<String, Object> map = JSON.parseObject(JSON.toJSONString(user),Map.class);
                token.setExpiration(new Date(System.currentTimeMillis()+90000000));
                map.put("expiresIn",token.getExpiresIn());
                token.setAdditionalInformation(map);
                return accessToken;
            }
        }, accessTokenConverter()));
        endpoints.tokenEnhancer(tokenEnhancerChain);

        // 配置TokenServices参数
        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        // 复用refresh token
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.SECONDS.toSeconds(1)); // 1天
        endpoints.tokenServices(tokenServices);
    }

    private List<TokenGranter> getTokenGranters(AuthorizationCodeServices authorizationCodeServices, TokenStore tokenStore, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        return new ArrayList<>(Arrays.asList(
                new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory),
                new JJKJRefreshTokenGranter(false,tokenStore, tokenServices,clientDetailsService,requestFactory),
                new JJKJPhoneSmsTokenGranter(userDetailsService,tokenServices, clientDetailsService, requestFactory),
                new JJKJPasswordTokenGranter(userDetailsService,tokenServices, clientDetailsService, requestFactory )
        ));
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("android")
                .scopes("xx") //此处的scopes是无用的，可以随意设置
                .secret("{noop}android")
                .redirectUris("http://www.baidu.com")
                .authorizedGrantTypes("pwd","phone_sms","authorization_code", "refresh_token")
                .and()
                .withClient("webapp")
                .scopes("xx")
                .authorizedGrantTypes("implicit");
    }

}