package com.penelopewu.shiro.modular.realm.demo;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        User user = null;
        CustomizedToken customizedToken = (CustomizedToken) token;
        String email = customizedToken.getUsername();
        user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        Object principal = email;
        Object credentials = user.getPassword();
        String realName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(email);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realName);
        return info;
    }
}
