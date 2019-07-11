package com.ironmadness.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,  USER_CHANEL, ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
