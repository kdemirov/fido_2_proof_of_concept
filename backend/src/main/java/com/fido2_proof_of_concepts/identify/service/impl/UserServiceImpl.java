package com.fido2_proof_of_concepts.identify.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.RegisterCommand;
import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.model.aggregate.User;
import com.fido2_proof_of_concepts.identify.model.dto.UserDetailsDto;
import com.fido2_proof_of_concepts.identify.model.identifiers.UserId;
import com.fido2_proof_of_concepts.identify.repository.JwtRepository;
import com.fido2_proof_of_concepts.identify.repository.UserRepository;
import com.fido2_proof_of_concepts.identify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User service implementation.
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtRepository jwtRepository;
    private final DefaultService defaultIdentifyService;

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public CompletableFuture<String> register(RegisterCommand cmd) {
        String encodedPassword = passwordEncoder.encode(cmd.getPassword());
        RegisterCommand command = new RegisterCommand(cmd.getName(),
                cmd.getSurname(), cmd.getUsername(), encodedPassword);
        return defaultIdentifyService.dispatch(command);
    }

    @Override
    public Optional<UserDetailsDto> getCurrentUser(HttpServletRequest request)
            throws JsonProcessingException {
        return this.jwtRepository.getCurrentUser(request);

    }

    @Override
    public Optional<User> findById(UserId userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByFidoUser(FidoUser user) {
        return this.userRepository.findByFidoUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList()));

    }
}
