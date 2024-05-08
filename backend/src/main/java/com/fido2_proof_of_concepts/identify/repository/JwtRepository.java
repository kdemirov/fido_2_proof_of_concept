package com.fido2_proof_of_concepts.identify.repository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.model.dto.UserDetailsDto;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Repository
public class JwtRepository {

    /**
     * Gets Authenticated user.
     *
     * @param request http servlet request.
     * @return optional of {@link UserDetailsDto}
     * @throws JsonProcessingException
     */
    public Optional<UserDetailsDto> getCurrentUser(HttpServletRequest request) throws JsonProcessingException {
        String header = request.getHeader(Constants.HEADER);
        String token = header.replace(Constants.TOKEN_PREFIX, "");
        String userJWT = JWT.require(Algorithm.none())
                .build()
                .verify(token)
                .getSubject();
        UserDetailsDto userDetailsDto = new ObjectMapper()
                .readValue(userJWT, UserDetailsDto.class);
        return Optional.of(userDetailsDto);
    }
}
