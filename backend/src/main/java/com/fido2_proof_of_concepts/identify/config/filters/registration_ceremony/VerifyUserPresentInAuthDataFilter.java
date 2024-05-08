package com.fido2_proof_of_concepts.identify.config.filters.registration_ceremony;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony.VerifyThatUserPresentInAuthDataIsSetCommand;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Verify User Present flag is set in auth data filter.
 */
@Component
@RequiredArgsConstructor
@Order(Constants.NINTH_FILTER_REGISTRATION_CEREMONY)
public class VerifyUserPresentInAuthDataFilter extends OncePerRequestFilter
        implements CeremonyFilterInterface {

    private final DefaultService defaultIdentifyService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CeremonyFilterInterface.super.doFilterInternal(request, response, filterChain,
                Constants.REGISTRATION_CEREMONY_URL, HttpStatus.BAD_REQUEST.value());
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        RegistrationCeremonyId registrationCeremonyId = (RegistrationCeremonyId) request.getAttribute(Constants.REGISTRATION_CEREMONY_ID_KEY);
        VerifyThatUserPresentInAuthDataIsSetCommand cmd = new VerifyThatUserPresentInAuthDataIsSetCommand(registrationCeremonyId);
        defaultIdentifyService.dispatch(cmd);
        filterChain.doFilter(request, response);
    }
}
