package com.fido2_proof_of_concepts.identify.config.filters.authentication_ceremony;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.config.authtokens.FidoUserAuthenticatorToken;
import com.fido2_proof_of_concepts.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.model.aggregate.User;
import com.fido2_proof_of_concepts.identify.model.dto.UserDetailsDto;
import com.fido2_proof_of_concepts.identify.model.enumerations.Role;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.AuthenticationCeremonyInMemoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * Authentication ceremony successful filter.
 */
@AllArgsConstructor
public class AuthenticationCeremonySuccessfulFilter extends UsernamePasswordAuthenticationFilter
        implements CeremonyFilterInterface {

    private final AuthenticationCeremonyInMemoryRepository repository = new AuthenticationCeremonyInMemoryRepository();
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthenticationCeremonyId id = (AuthenticationCeremonyId) request.getAttribute(Constants.AUTHENTICATION_CEREMONY_ID_KEY);
        FidoUser fidoUser = repository.getFidoUser(id);
        byte[] authData = repository.getResponse(id).getAuthenticatorData();
        return authenticationManager.authenticate(
                new FidoUserAuthenticatorToken(authData, fidoUser, Collections.singleton(Role.ROLE_USER))
        );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CeremonyFilterInterface.super.doFilterInternal(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                chain,
                Constants.AUTHENTICATION_CEREMONY_URL,
                HttpStatus.UNAUTHORIZED.value()
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException {
        User user = (User) authResult.getPrincipal();
        String token = JWT.create()
                .withIssuer(Constants.JWT_ISSUER)
                .withSubject(new ObjectMapper().writeValueAsString(new UserDetailsDto(user.getUsername(),
                        user.getName(), user.getRole(), user.getId().getId(), Constants.FIDO_AUTHENTICATION_TYPE)))
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .sign(Algorithm.none());
        response.addHeader(Constants.HEADER, Constants.TOKEN_PREFIX + token);
        response.getWriter().append(token);
        response.getWriter().flush();
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = attemptAuthentication(request, response);
        if (authentication != null) {
            successfulAuthentication(request, response, filterChain, authentication);
        }
    }
}

