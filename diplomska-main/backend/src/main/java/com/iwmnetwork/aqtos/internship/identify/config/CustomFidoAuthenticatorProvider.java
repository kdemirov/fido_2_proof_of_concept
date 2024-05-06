package com.iwmnetwork.aqtos.internship.identify.config;

import com.iwmnetwork.aqtos.internship.identify.config.authtokens.FidoUserAuthenticatorToken;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
        UserDetails userDetails = this.userService.loadUserByUsername(user.getUsername());
        return new FidoUserAuthenticatorToken(userDetails, fidoUser, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(FidoUserAuthenticatorToken.class);
    }
}
