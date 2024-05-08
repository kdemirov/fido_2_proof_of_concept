package com.iwmnetwork.aqtos.internship.identify.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

/**
 * Roles.
 */
public enum Role implements GrantedAuthority {

    ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
