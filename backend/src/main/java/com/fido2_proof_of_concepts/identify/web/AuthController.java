package com.fido2_proof_of_concepts.identify.web;


import com.fido2_proof_of_concepts.identify.api.commands.LoginCommand;
import com.fido2_proof_of_concepts.identify.api.commands.RegisterCommand;
import com.fido2_proof_of_concepts.identify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * Authentication with password rest controller.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/identity")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Registers a user with given command
     *
     * @param cmd {@link RegisterCommand}
     * @return id of registered user.
     */
    @PostMapping("/register")
    public CompletableFuture<String> register(@RequestBody RegisterCommand cmd) {
        return this.userService.register(cmd);
    }

    /**
     * Entry point for login with username and password.
     *
     * @param cmd {@link LoginCommand}
     */
    @PostMapping("/login")
    public void login(@RequestBody LoginCommand cmd) {
    }
}
