package com.iwmnetwork.aqtos.internship.identify.config;

import com.iwmnetwork.aqtos.internship.identify.config.authtokens.FidoUserAuthenticatorToken;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Fido authentication provider.
 */
@Component
@AllArgsConstructor
public class CustomFidoAuthenticatorProvider implements AuthenticationProvider {

    private final UserService userService;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FidoUser fidoUser = (FidoUser) authentication.getCredentials();
        User user = this.userService.findByFidoUser(fidoUser)
                .orElseThrow(VerificationFailedException::new);
        return new FidoUserAuthenticatorToken(user, fidoUser, Collections.singleton(user.getRole()));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(FidoUserAuthenticatorToken.class);
    }
}
