package com.fido2_proof_of_concepts.identify.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fido2_proof_of_concepts.identify.api.commands.RegisterCommand;
import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.model.aggregate.User;
import com.fido2_proof_of_concepts.identify.model.dto.UserDetailsDto;
import com.fido2_proof_of_concepts.identify.model.identifiers.UserId;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * User service.
 */
public interface UserService extends UserDetailsService {

    /**
     * Finds user by given username
     *
     * @param username given username
     * @return Optional of {@link User}
     */
    Optional<User> findByUsername(String username);

    /**
     * Dispatches {@link RegisterCommand} command to {@link User} aggregate.
     *
     * @param cmd {@link RegisterCommand}
     * @return Completable future of string
     */
    CompletableFuture<String> register(RegisterCommand cmd);

    /**
     * Finds authenticated user.
     *
     * @param request http request
     * @return optional of {@link UserDetailsDto}
     * @throws JsonProcessingException
     */
    Optional<UserDetailsDto> getCurrentUser(HttpServletRequest request) throws JsonProcessingException;

    /**
     * Finds user by given user id.
     *
     * @param userId given user id
     * @return Optional of {@link User}
     */
    Optional<User> findById(UserId userId);

    /**
     * Finds user by given fido user.
     *
     * @param user give fido user
     * @return Optional of {@link User}
     */
    Optional<User> findByFidoUser(FidoUser user);
}
