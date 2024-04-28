package com.iwmnetwork.aqtos.identify.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iwmnetwork.aqtos.identify.demo.model.FidoUser;
import com.iwmnetwork.aqtos.identify.demo.model.aggregate.User;
import com.iwmnetwork.aqtos.identify.demo.model.dto.UserDetailsDto;
import com.iwmnetwork.aqtos.identify.demo.model.identifiers.UserId;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);

    Optional<UserDetailsDto> getCurrentUser(HttpServletRequest request) throws JsonProcessingException;

    Optional<User> findById(UserId userId);

    Optional<User> findByFidoUser(FidoUser user);


}
