package com.iwmnetwork.aqtos.internship.identify.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.config.authtokens.FidoUserAuthenticatorToken;
import com.iwmnetwork.aqtos.internship.identify.model.dto.UserDetailsDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(Constants.HEADER);
        if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        FidoUserAuthenticatorToken token = getToken(header);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    public FidoUserAuthenticatorToken getToken(String header) throws JsonProcessingException {
        String user = JWT.require(Algorithm.none())
                .build()
                .verify(header.replace(Constants.TOKEN_PREFIX, ""))
                .getSubject();
        if (user == null) {
            return null;
        }
        UserDetailsDto userDetailsDto = new ObjectMapper().readValue(user, UserDetailsDto.class);
        return new FidoUserAuthenticatorToken(userDetailsDto.getUsername(),
                "",
                Collections.singletonList(userDetailsDto.getRole()));
    }
}
