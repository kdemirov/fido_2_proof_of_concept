package com.iwmnetwork.aqtos.internship.identify.config.filters.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.DecodeJsonClientDataCommand;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.RegistrationCeremonyInMemoryRepository;
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
 * Decode client data for registration ceremony filter.
 */
@RequiredArgsConstructor
@Component
@Order(Constants.SECOND_FILTER_REGISTRATION_CEREMONY)
public class RegistrationDecodeClientDataFilter extends OncePerRequestFilter
        implements RegistrationCeremonyFilterInterface {

    private final DefaultIdentifyService defaultIdentifyService;
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
        String clientDataHash = repository.getJsonText(registrationCeremonyId);
        DecodeJsonClientDataCommand cmd = new DecodeJsonClientDataCommand(registrationCeremonyId, clientDataHash);
        defaultIdentifyService.dispatch(cmd);
        filterChain.doFilter(request, response);
    }
}
