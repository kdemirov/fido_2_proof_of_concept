package com.fido2_proof_of_concepts.identify.config.filters.authentication_ceremony;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony.AuthenticationVerifyChallengeCommand;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.AllArgsConstructor;
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
 * Verify challenge for authentication ceremony filter.
 */
@AllArgsConstructor
public class VerifyChallengeFilter extends UsernamePasswordAuthenticationFilter
        implements CeremonyFilterInterface {

    private final DefaultService defaultIdentifyService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CeremonyFilterInterface.super.doFilterInternal(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                chain,
                Constants.AUTHENTICATION_CEREMONY_URL,
                HttpStatus.UNAUTHORIZED.value()
        );
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationCeremonyId id = (AuthenticationCeremonyId) request.getAttribute(Constants.AUTHENTICATION_CEREMONY_ID_KEY);
        AuthenticationVerifyChallengeCommand cmd = new AuthenticationVerifyChallengeCommand(id);
        defaultIdentifyService.dispatch(cmd);
        filterChain.doFilter(request, response);
    }
}
