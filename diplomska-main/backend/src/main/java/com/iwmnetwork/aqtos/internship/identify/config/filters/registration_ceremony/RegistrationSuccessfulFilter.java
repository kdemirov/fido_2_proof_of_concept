package com.iwmnetwork.aqtos.internship.identify.config.filters.registration_ceremony;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.RegistrationCeremonyInMemoryRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.iwmnetwork.aqtos.internship.identify.service.impl.RegistrationCeremonyImpl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

/**
 * Registration Successful filter.
 */
@Component
@RequiredArgsConstructor
@Order(Constants.THIRTEENTH_FILTER_REGISTRATION_CEREMONY)
public class RegistrationSuccessfulFilter extends OncePerRequestFilter
        implements RegistrationCeremonyFilterInterface {

    private final RegistrationCeremonyImpl registrationCeremony;
    private final RegistrationCeremonyInMemoryRepository repository;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        RegistrationCeremonyFilterInterface.super.doFilterInternal(request, response, filterChain);
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
