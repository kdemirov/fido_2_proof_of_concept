package com.fido2_proof_of_concepts.identify.config.filters.authentication_ceremony;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony.FidoUserAuthenticationStartCommand;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.fido2_proof_of_concepts.identify.model.dto.PublicKeyCredentialRequestResponse;
import com.fido2_proof_of_concepts.identify.model.exceptions.Fido2Exception;
import com.fido2_proof_of_concepts.identify.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Start Authentication ceremony.
 */
public class StartAuthenticationCeremonyFilter extends UsernamePasswordAuthenticationFilter implements
        CeremonyFilterInterface {

    private final DefaultService defaultIdentifyService;
    private final UserService userService;

    public StartAuthenticationCeremonyFilter(DefaultService defaultIdentifyService,
                                             UserService userService) {
        this.defaultIdentifyService = defaultIdentifyService;
        this.userService = userService;
        setFilterProcessesUrl(Constants.AUTHENTICATION_CEREMONY_URL);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CeremonyFilterInterface.super.doFilterInternal((HttpServletRequest) request,
                (HttpServletResponse) response, chain,
                Constants.AUTHENTICATION_CEREMONY_URL,   HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        PublicKeyCredentialRequestResponse credentialRequestResponse = null;
        try {
            credentialRequestResponse = new ObjectMapper().readValue(request.getInputStream(), PublicKeyCredentialRequestResponse.class);
            FidoUserAuthenticationStartCommand cmd = new FidoUserAuthenticationStartCommand(
                    credentialRequestResponse.getAuthenticationCeremonyId(),
                    credentialRequestResponse.getId(),
                    credentialRequestResponse.getSignature(),
                    credentialRequestResponse.getAuthData(),
                    credentialRequestResponse.getUserHandle(),
                    credentialRequestResponse.getClientDataJSON(),
                    credentialRequestResponse.getType()
            );
            defaultIdentifyService.dispatch(cmd);
            request.setAttribute(Constants.AUTHENTICATION_CEREMONY_ID_KEY, cmd.getCeremonyId());
            filterChain.doFilter(request, response);
        } catch (Exception o_O) {
            throw new Fido2Exception(o_O);
        }
    }
}

