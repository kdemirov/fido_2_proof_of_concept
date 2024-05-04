package com.iwmnetwork.aqtos.internship.identify.config.filters.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.RegistrationVerifyClientDataTypeCommand;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
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
 * Verify client data type for registration ceremony filter.
 */
@Component
@RequiredArgsConstructor
@Order(Constants.THIRD_FILTER_REGISTRATION_CEREMONY)
public class RegistrationVerifyClientDataTypeFilter extends OncePerRequestFilter
        implements RegistrationCeremonyFilterInterface {

    private final DefaultIdentifyService defaultIdentifyService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        RegistrationCeremonyFilterInterface.super.doFilterInternal(request, response, filterChain);
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        RegistrationCeremonyId registrationCeremonyId = (RegistrationCeremonyId) request.getAttribute(Constants.REGISTRATION_CEREMONY_ID_KEY);
        RegistrationVerifyClientDataTypeCommand command = new RegistrationVerifyClientDataTypeCommand(registrationCeremonyId);
        defaultIdentifyService.dispatch(command);
        filterChain.doFilter(request, response);
    }
}
