package com.iwmnetwork.aqtos.internship.identify.web;


import com.iwmnetwork.aqtos.internship.identify.api.commands.LoginCommand;
import com.iwmnetwork.aqtos.internship.identify.api.commands.RegisterCommand;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/identity")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public CompletableFuture<String> register(@RequestBody RegisterCommand cmd) {
        return this.userService.register(cmd);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginCommand cmd) {
    }
}
