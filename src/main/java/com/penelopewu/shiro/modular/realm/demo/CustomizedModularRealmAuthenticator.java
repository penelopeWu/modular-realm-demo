package com.penelopewu.shiro.modular.realm.demo;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

public class CustomizedModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        assertRealmsConfigured();
        CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
        String loginType = customizedToken.getLoginType();
        Collection<Realm> realms = getRealms();
        Collection<Realm> typeRealms = new ArrayList<Realm>();
        for (Realm realm : realms) {
            if (realm.getName().contains(loginType)) {
                typeRealms.add(realm);
            }
        }

        if (typeRealms.size() == 1) {
            return doSingleRealmAuthentication(typeRealms.iterator().next(),customizedToken);
        }else {
            return doMultiRealmAuthentication(typeRealms, customizedToken);
        }
    }
}
