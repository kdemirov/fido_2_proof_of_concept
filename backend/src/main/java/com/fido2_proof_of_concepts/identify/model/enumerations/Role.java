package com.fido2_proof_of_concepts.identify.model.enumerations;

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
