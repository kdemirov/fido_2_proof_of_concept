package com.iwmnetwork.aqtos.internship.identify.api.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Login command.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginCommand {
    private String username;
    private String password;
}
