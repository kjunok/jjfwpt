package com.jjkj.support.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.zookeeper.Login;

/**
 * @author WangJun
 * @version 2016年5月20日 下午3:44:45
 */
public final class LoginHelper {
    private LoginHelper() {
    }

    /** 用户登录 */
    public static final Boolean login(Login user, String host) {
        //TODO
     return true;
    }
}
