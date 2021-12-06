package com.cegeka.switchfully.security.security;

import org.springframework.security.core.GrantedAuthority;

public class ArmyGrantedAuthority implements GrantedAuthority {
    private final String authority;

    public ArmyGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
