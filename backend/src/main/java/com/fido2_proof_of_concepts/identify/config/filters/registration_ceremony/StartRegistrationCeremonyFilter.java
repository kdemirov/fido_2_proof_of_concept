package com.fido2_proof_of_concepts.identify.config.filters.registration_ceremony;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony.FidoRegistrationStartCommand;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.fido2_proof_of_concepts.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.fido2_proof_of_concepts.identify.model.exceptions.Fido2Exception;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.RegistrationCeremonyInMemoryRepository;
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
 * Start registration ceremony filter.
 */
@Component
@RequiredArgsConstructor
@Order(Constants.FIRST_FILTER_REGISTRATION_CEREMONY)
public class StartRegistrationCeremonyFilter extends OncePerRequestFilter
        implements CeremonyFilterInterface {

    private final DefaultService defaultIdentifyService;
    private final ObjectMapper objectMapper;
    private final RegistrationCeremonyInMemoryRepository repository;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CeremonyFilterInterface.super.doFilterInternal(request, response, filterChain,
                Constants.REGISTRATION_CEREMONY_URL, HttpStatus.BAD_REQUEST.value());
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        PublicKeyCredentialCreationResponse publicKeyCredentialCreationResponse = null;
        try {
            publicKeyCredentialCreationResponse = objectMapper.readValue(request.getInputStream(),
                    PublicKeyCredentialCreationResponse.class);
            FidoRegistrationStartCommand cmd = new FidoRegistrationStartCommand(
                    publicKeyCredentialCreationResponse.getRegistrationCeremonyId(),
                    publicKeyCredentialCreationResponse.getId(),
                    publicKeyCredentialCreationResponse.getType(),
                    publicKeyCredentialCreationResponse.getAttestationObject(),
                    publicKeyCredentialCreationResponse.getUsername(),
                    publicKeyCredentialCreationResponse.getClientDataHash()
            );
            defaultIdentifyService.dispatch(cmd);
            request.setAttribute(Constants.REGISTRATION_CEREMONY_ID_KEY, cmd.getCeremonyId());
            repository.setPublicKeyCredentialResponse((RegistrationCeremonyId) cmd.getCeremonyId(),
                    publicKeyCredentialCreationResponse);
            filterChain.doFilter(request, response);
        } catch (Exception o_O) {
            throw new Fido2Exception(o_O);
        }
    }
}
