package com.jjkj.auth.token;

import com.alibaba.fastjson.JSONObject;
import com.jjkj.model.TUserInfo;
import com.jjkj.util.AESUtil;
import com.jjkj.util.MathUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class JJKJTokenConverter implements TokenEnhancer, AccessTokenConverter, InitializingBean {

    /**
     * Field name for token id.
     */
    public static final String TOKEN_ID = AccessTokenConverter.JTI;

    /**
     * Field name for access token id.
     */
    public static final String ACCESS_TOKEN_ID = AccessTokenConverter.ATI;
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    private JsonParser objectMapper = JsonParserFactory.create();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        return tokenConverter.convertAccessToken(token, authentication);
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return tokenConverter.extractAccessToken(value, map);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return tokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
        Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
        TUserInfo tUserInfo = (TUserInfo) authentication.getPrincipal();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a",System.currentTimeMillis());
        jsonObject.put("b",tUserInfo.getId());
        jsonObject.put("c",tUserInfo.getAccount());
        result.setValue(AESUtil.encrypt(jsonObject.toJSONString()));
        System.out.println(AESUtil.decrypt(result.getValue()));
        result.setAdditionalInformation(info);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(AESUtil.decrypt("0FD7AC8F562DAFEB60652FCFC6AB9EDB48EE8C4453E3CC47CC2D41E0D81A8EA7757A884C9C8B3D7C562E025E6B0360C5B342C22196044F111849C1EE75C3AE9B"));
    }

}