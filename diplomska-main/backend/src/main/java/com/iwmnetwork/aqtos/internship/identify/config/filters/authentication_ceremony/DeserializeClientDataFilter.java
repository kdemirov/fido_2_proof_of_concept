package com.iwmnetwork.aqtos.internship.identify.config.filters.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony.DeserializeClientDataJsonCommand;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.dom4j.datatype.DatatypeElementFactory;
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
 * Deserialize client data filter.
 */
@AllArgsConstructor
public class DeserializeClientDataFilter extends UsernamePasswordAuthenticationFilter
        implements CeremonyFilterInterface {

    private final DefaultIdentifyService defaultIdentifyService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CeremonyFilterInterface.super.doFilterInternal(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                chain,
                Constants.AUTHENTICATION_CEREMONY_URL,
                401
        );
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationCeremonyId authenticationCeremonyId = (AuthenticationCeremonyId) request.getAttribute(Constants.AUTHENTICATION_CEREMONY_ID_KEY);
        DeserializeClientDataJsonCommand command = new DeserializeClientDataJsonCommand(
                authenticationCeremonyId
        );
        defaultIdentifyService.dispatch(command);
        filterChain.doFilter(request, response);
    }
}
