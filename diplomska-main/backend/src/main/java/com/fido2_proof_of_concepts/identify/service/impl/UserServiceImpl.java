package com.iwmnetwork.aqtos.internship.identify.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iwmnetwork.aqtos.internship.identify.api.commands.RegisterCommand;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.model.dto.UserDetailsDto;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import com.iwmnetwork.aqtos.internship.identify.repository.JwtRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.UserRepository;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
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
    private final DefaultIdentifyService defaultIdentifyService;

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
