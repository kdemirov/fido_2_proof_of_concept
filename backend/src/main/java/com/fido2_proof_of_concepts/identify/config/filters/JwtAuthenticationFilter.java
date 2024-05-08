package com.fido2_proof_of_concepts.identify.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fido2_proof_of_concepts.identify.api.commands.LoginCommand;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.model.aggregate.User;
import com.fido2_proof_of_concepts.identify.model.dto.UserDetailsDto;
import com.fido2_proof_of_concepts.identify.model.enumerations.Role;
import com.fido2_proof_of_concepts.identify.service.UserService;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * Authentication filter with username and password method.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final Environment environment;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserService userService,
                                   PasswordEncoder passwordEncoder, Environment environment) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.environment = environment;
        setFilterProcessesUrl(environment.getProperty("fido.login_url",
                String.class, "/api/identity/login"));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        LoginCommand credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginCommand.class);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        UserDetails user = userService.loadUserByUsername(credentials.getUsername());
        if (!this.passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad Credentials");
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                credentials.getPassword(), Collections.singletonList(Role.ROLE_USER)));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        User user = this.userService.findByUsername((String) authResult.getPrincipal())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        String token = JWT.create()
                .withIssuer(Constants.JWT_ISSUER)
                .withSubject(new ObjectMapper().writeValueAsString(new UserDetailsDto(user.getUsername(),
                        user.getName(), user.getRole(), user.getId().getId(), Constants.PASSWORD_AUTHENTICATION_TYPE)))
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .sign(Algorithm.none());
        response.addHeader(Constants.HEADER, Constants.TOKEN_PREFIX + token);
        response.getWriter().append(token);
        response.getWriter().flush();
    }
}

