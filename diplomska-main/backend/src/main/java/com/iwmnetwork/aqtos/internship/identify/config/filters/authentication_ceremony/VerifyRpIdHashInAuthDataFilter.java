package com.iwmnetwork.aqtos.internship.identify.config.filters.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony.AuthenticationVerifyRpIdHashInAuthDataCommand;
import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.VerifyRpIdHashInAuthDataCommand;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Verify rp id hash authentication ceremony filter.
 */
@AllArgsConstructor
public class VerifyRpIdHashInAuthDataFilter extends UsernamePasswordAuthenticationFilter
        implements CeremonyFilterInterface {

    private final DefaultIdentifyService defaultIdentifyService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CeremonyFilterInterface.super.doFilterInternal(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                chain,
                Constants.AUTHENTICATION_CEREMONY_URL,
                HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationCeremonyId id = (AuthenticationCeremonyId) request.getAttribute(Constants.AUTHENTICATION_CEREMONY_ID_KEY);
        AuthenticationVerifyRpIdHashInAuthDataCommand command = new AuthenticationVerifyRpIdHashInAuthDataCommand(id);
        defaultIdentifyService.dispatch(command);
        filterChain.doFilter(request, response);
    }
}
