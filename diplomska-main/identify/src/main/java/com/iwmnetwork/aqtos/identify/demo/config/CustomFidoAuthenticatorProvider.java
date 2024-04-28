package com.iwmnetwork.aqtos.identify.demo.config;

import com.iwmnetwork.aqtos.identify.demo.api.commands.FidoUserAuthenticateCommand;
import com.iwmnetwork.aqtos.identify.demo.config.authtokens.FidoUserAuthenticatorToken;
import com.iwmnetwork.aqtos.identify.demo.model.FidoUser;
import com.iwmnetwork.aqtos.identify.demo.model.aggregate.User;
import com.iwmnetwork.aqtos.identify.demo.repository.webauthn.crypto.JsonParser;
import com.iwmnetwork.aqtos.identify.demo.repository.webauthn.exceptions.VerificationFailedException;
import com.iwmnetwork.aqtos.identify.demo.repository.FidoUserRepository;
import com.iwmnetwork.aqtos.identify.demo.service.UserService;
import com.iwmnetwork.aqtos.identify.demo.service.impl.RelyingPartyService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomFidoAuthenticatorProvider implements AuthenticationProvider {

    private final UserService userService;
    private final RelyingPartyService relyingPartyService;
    private final FidoUserRepository repository;

    public CustomFidoAuthenticatorProvider(UserService userService,
                                           RelyingPartyService relyingPartyService,
                                           FidoUserRepository repository) {
        this.userService = userService;
        this.relyingPartyService = relyingPartyService;
        this.repository = repository;
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FidoUserAuthenticateCommand cmd = (FidoUserAuthenticateCommand) authentication.getCredentials();
        if (!this.relyingPartyService.decodeAndVerifyAssertionObject(cmd)) {
            throw new VerificationFailedException();
        }
        byte[] credential = JsonParser.JsonToByteArray(cmd.getId());
        FidoUser fidoUser = this.repository.findByCredentialId(credential)
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
        User user = this.userService.findByFidoUser(fidoUser)
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
        UserDetails userDetails = this.userService.loadUserByUsername(user.getUsername());
        return new FidoUserAuthenticatorToken(userDetails, cmd, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(FidoUserAuthenticatorToken.class);
    }
}
