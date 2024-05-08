package com.iwmnetwork.aqtos.internship.identify.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.model.dto.UserDetailsDto;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Authentication read rest controller.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/identity")
@RequiredArgsConstructor
public class AuthReadController {

    private final UserService userService;

    /**
     * Gets authenticated user.
     *
     * @param request HttpServlet request
     * @return {@link UserDetailsDto}
     * @throws JsonProcessingException
     */
    @GetMapping
    public ResponseEntity<UserDetailsDto> getCurrentUser(HttpServletRequest request) throws JsonProcessingException {
        Optional<UserDetailsDto> user = this.userService.getCurrentUser(request);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Returns a user by given id.
     *
     * @param id given user's id
     * @return {@link User}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        UserId userId = new UserId(id);
        Optional<User> user = this.userService.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
