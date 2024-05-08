package com.iwmnetwork.aqtos.internship.identify.config.authtokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Fido user authenticator token.
 */
public class FidoUserAuthenticatorToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;

    public FidoUserAuthenticatorToken(Object principal, Object credentials) {
        super((Collection) null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public FidoUserAuthenticatorToken(Object principal,
                                      Object credentials,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
