package com.iwmnetwork.aqtos.internship.identify.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.model.dto.UserDetailsDto;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);

    Optional<UserDetailsDto> getCurrentUser(HttpServletRequest request) throws JsonProcessingException;

    Optional<User> findById(UserId userId);

    Optional<User> findByFidoUser(FidoUser user);


}
