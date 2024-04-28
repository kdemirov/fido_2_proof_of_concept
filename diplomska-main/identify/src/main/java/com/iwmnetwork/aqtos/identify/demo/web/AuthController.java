package com.iwmnetwork.aqtos.identify.demo.web;

import com.iwmnetwork.aqtos.identify.demo.api.commands.LoginCommand;
import com.iwmnetwork.aqtos.identify.demo.api.commands.RegisterCommand;
import com.iwmnetwork.aqtos.identify.demo.service.DefaultIdentifyService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/identity")
public class AuthController {

    private final DefaultIdentifyService defaultIdentifyService;

    public AuthController(DefaultIdentifyService defaultIdentifyService) {
        this.defaultIdentifyService = defaultIdentifyService;
    }

    @PostMapping("/register")
    public CompletableFuture<String> register(@RequestBody RegisterCommand cmd) {
        return this.defaultIdentifyService.dispatch(new RegisterCommand(cmd.getName(),
                cmd.getSurname(), cmd.getUsername(), cmd.getPassword()));
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginCommand cmd) {

    }

}
