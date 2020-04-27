package com.rman.config.shiro;

import com.rman.entity.UserBean;
import com.rman.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Description :
 * @Author : ziming
 * @Date: 2020-04-22 11:09
 */

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String token = principalCollection.toString();
        String username = JWTUtil.getUsername(token);
        UserBean user = userService.getUserBean(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole());

        HashSet<String> permissionSet = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }
    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String username = JWTUtil.getUsername(token);
        if(username==null){
            throw new UnauthorizedException("token invalid");
        }
        UserBean user = userService.getUserBean(username);
        if (user==null){
            throw new UnauthorizedException("User didn't existed!");
        }

        if (!JWTUtil.verify(token,username,user.getPassword())){
            throw new UnauthorizedException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token,
                getName());
    }
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
}
