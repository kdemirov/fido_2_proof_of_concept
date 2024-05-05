package com.iwmnetwork.aqtos.internship.identify.config.filters.registration_ceremony;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.FidoStartCommand;
import com.iwmnetwork.aqtos.internship.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.exceptions.Fido2Exception;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.RegistrationCeremonyInMemoryRepository;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
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
public class StartCeremonyFilter extends OncePerRequestFilter
        implements CeremonyFilterInterface {

    private final DefaultIdentifyService defaultIdentifyService;
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
            FidoStartCommand cmd = new FidoStartCommand(
                    new RegistrationCeremonyId(publicKeyCredentialCreationResponse.getRegistrationCeremonyId()),
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
