package com.iwmnetwork.aqtos.internship.identify.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwmnetwork.aqtos.internship.discussion.config.JwtConstants;
import com.iwmnetwork.aqtos.internship.discussion.model.exceptions.NotAuthenticatedException;
import com.iwmnetwork.aqtos.internship.identify.model.dto.UserDetailsDto;
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


public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(JwtConstants.HEADER);
        if (header == null || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new NotAuthenticatedException();
        }
        UsernamePasswordAuthenticationToken token = getToken(header);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getToken(String header) throws JsonProcessingException {
        String user = JWT.require(Algorithm.none())
                .build()
                .verify(header.replace(JwtConstants.TOKEN_PREFIX, ""))
                .getSubject();
        if (user == null) {
            return null;
        }
        UserDetailsDto userDetails = new ObjectMapper().readValue(user, UserDetailsDto.class);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                "",
                Collections.singletonList(userDetails.getRole()));
    }

}
