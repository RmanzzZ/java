package com.rman.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Description :
 * @Author : ziming
 * @Date: 2020-04-22 11:11
 */
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
