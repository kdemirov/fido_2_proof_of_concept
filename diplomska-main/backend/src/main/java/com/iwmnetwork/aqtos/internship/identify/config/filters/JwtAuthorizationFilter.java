package com.iwmnetwork.aqtos.internship.identify.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.config.authtokens.FidoUserAuthenticatorToken;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.model.dto.UserDetailsDto;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Jwt Authorization filter.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(Constants.HEADER);
        if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        AbstractAuthenticationToken token = getToken(header);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    public AbstractAuthenticationToken getToken(String header) throws JsonProcessingException {
        String userJwt = JWT.require(Algorithm.none())
                .build()
                .verify(header.replace(Constants.TOKEN_PREFIX, ""))
                .getSubject();
        if (userJwt == null) {
            return null;
        }
        UserDetailsDto userDetailsDto = new ObjectMapper().readValue(userJwt, UserDetailsDto.class);
        User user = userService.findByUsername(userDetailsDto.getUsername())
                .orElseThrow(VerificationFailedException::new);
        return userDetailsDto.getType().equals(Constants.PASSWORD_AUTHENTICATION_TYPE)
                ? new UsernamePasswordAuthenticationToken(userDetailsDto.getUsername(),
                user.getPassword(),
                Collections.singletonList(userDetailsDto.getRole()))
                : new FidoUserAuthenticatorToken(userDetailsDto.getUsername(),
                user.getFidoUser(),
                Collections.singletonList(userDetailsDto.getRole()));
    }
}
