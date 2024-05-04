package com.iwmnetwork.aqtos.internship.identify.config.filters.registration_ceremony;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.FidoRegistrationStartCommand;
import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.exceptions.Fido2Exception;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import lombok.RequiredArgsConstructor;
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
 * Start registration ceremony filter.
 */
@Component
@RequiredArgsConstructor
@Order(Constants.FIRST_FILTER_REGISTRATION_CEREMONY)
public class StartRegistrationCeremonyFilter extends OncePerRequestFilter
        implements RegistrationCeremonyFilterInterface {

    private final DefaultIdentifyService defaultIdentifyService;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        RegistrationCeremonyFilterInterface.super.doFilterInternal(request, response, filterChain);
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        PublicKeyCredentialCreationResponse publicKeyCredentialCreationResponse = null;
        try {
            publicKeyCredentialCreationResponse = objectMapper.readValue(request.getInputStream(),
                    PublicKeyCredentialCreationResponse.class);
            FidoRegistrationStartCommand cmd = new FidoRegistrationStartCommand(
                    new RegistrationCeremonyId(publicKeyCredentialCreationResponse.getRegistrationCeremonyId()),
                    publicKeyCredentialCreationResponse.getId(),
                    publicKeyCredentialCreationResponse.getType(),
                    publicKeyCredentialCreationResponse.getAttestationObject(),
                    publicKeyCredentialCreationResponse.getUsername(),
                    publicKeyCredentialCreationResponse.getClientDataHash()
            );
            defaultIdentifyService.dispatch(cmd);
            request.setAttribute(Constants.REGISTRATION_CEREMONY_ID_KEY, cmd.getRegistrationCeremonyId());
            request.setAttribute("clientDataHash", cmd.getClientDataHash());
            request.setAttribute("creationResponse", publicKeyCredentialCreationResponse);
            filterChain.doFilter(request, response);
        } catch (Exception o_O) {
            throw new Fido2Exception(o_O);
        }
    }
}
