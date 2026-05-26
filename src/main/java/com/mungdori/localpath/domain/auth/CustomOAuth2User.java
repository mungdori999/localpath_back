package com.mungdori.localpath.domain.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2ResponseUser oAuth2ResponseUser;

    public CustomOAuth2User(OAuth2ResponseUser oAuth2ResponseUser) {

        this.oAuth2ResponseUser = oAuth2ResponseUser;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add((GrantedAuthority) () -> oAuth2ResponseUser.role());

        return collection;
    }

    @Override
    public String getName() {

        return oAuth2ResponseUser.name();
    }

    public String getEmail() {

        return oAuth2ResponseUser.email();
    }
}
