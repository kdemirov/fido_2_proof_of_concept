package com.iwmnetwork.aqtos.internship.identify.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;


public class JwtAuthenticationFidoFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RelyingPartyService service;
    private final UserService userService;
    private final Environment environment;

    public JwtAuthenticationFidoFilter(AuthenticationManager authenticationManager,
                                       RelyingPartyService service,
                                       UserService userService, Environment environment) {
        this.authenticationManager = authenticationManager;
        this.service = service;
        this.userService = userService;
        this.environment = environment;
        setFilterProcessesUrl(environment.getProperty("fido2.login_finish_url",
                String.class, "/api/identity/login_finish"));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        FidoUserAuthenticateCommand credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), FidoUserAuthenticateCommand.class);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        assert credentials != null;

        if (!this.service.decodeAndVerifyAssertionObject(credentials)) {
            throw new VerificationFailedException();
        }
        return authenticationManager.authenticate(new FidoUserAuthenticatorToken(credentials.getAuthData(),
                credentials,
                Collections.singletonList(Role.ROLE_USER)));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        User user = this.userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDetails.getUsername()));
        String token = JWT.create()
                .withIssuer("auth0")
                .withSubject(new ObjectMapper().writeValueAsString(new UserDetailsDto(user.getUsername(),
                        user.getName(),user.getRole(), user.getId().getId())))
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .sign(Algorithm.none());
        response.addHeader(Constants.HEADER, Constants.TOKEN_PREFIX + token);
        response.getWriter().append(token);
        response.getWriter().flush();
    }
}

