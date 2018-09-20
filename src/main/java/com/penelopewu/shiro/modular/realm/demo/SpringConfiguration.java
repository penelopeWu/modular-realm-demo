package com.penelopewu.shiro.modular.realm.demo;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SpringConfiguration {

    @Bean(name = "sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
        sessionManager.setSessionValidationSchedulerEnabled(true);

        return sessionManager;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        UserRealm userRealm = new UserRealm();
        AdminRealm adminRealm = new AdminRealm();
        Collection<Realm> realms = new ArrayList<>();
        realms.add(userRealm);
        ((ArrayList<Realm>) realms).add(adminRealm);
        securityManager.setRealms(realms);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean(name = "authenticator")
    public CustomizedModularRealmAuthenticator authenticator(){
        CustomizedModularRealmAuthenticator authenticator = new CustomizedModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    @Bean("userRealm")
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    @Bean("adminRealm")
    public AdminRealm adminRealm(){
        AdminRealm adminRealm = new AdminRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        adminRealm.setCredentialsMatcher(matcher);
        return adminRealm;
    }
}
