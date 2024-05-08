package com.fido2_proof_of_concepts.identify.config.filters.registration_ceremony;

import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.fido2_proof_of_concepts.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.RegistrationCeremonyInMemoryRepository;
import com.fido2_proof_of_concepts.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.fido2_proof_of_concepts.identify.service.RegistrationCeremonyService;
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
 * Registration Successful filter.
 */
@Component
@RequiredArgsConstructor
@Order(Constants.THIRTEENTH_FILTER_REGISTRATION_CEREMONY)
public class SuccessfulFilter extends OncePerRequestFilter
        implements CeremonyFilterInterface {

    private final RegistrationCeremonyService registrationCeremony;
    private final RegistrationCeremonyInMemoryRepository repository;

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
        PublicKeyCredentialCreationResponse res = repository.getCredentialCreationResponse(registrationCeremonyId);

        boolean registrationSuccessful = registrationCeremony.registrationSuccessful(res);

        if (!registrationSuccessful) {
            throw new VerificationFailedException();
        }
    }
}
