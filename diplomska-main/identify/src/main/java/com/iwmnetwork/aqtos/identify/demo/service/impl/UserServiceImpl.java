package com.iwmnetwork.aqtos.identify.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iwmnetwork.aqtos.identify.demo.model.FidoUser;
import com.iwmnetwork.aqtos.identify.demo.model.aggregate.User;
import com.iwmnetwork.aqtos.identify.demo.model.dto.UserDetailsDto;
import com.iwmnetwork.aqtos.identify.demo.model.identifiers.UserId;
import com.iwmnetwork.aqtos.identify.demo.repository.JwtRepository;
import com.iwmnetwork.aqtos.identify.demo.repository.UserRepository;
import com.iwmnetwork.aqtos.identify.demo.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtRepository jwtRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtRepository jwtRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtRepository = jwtRepository;
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
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
