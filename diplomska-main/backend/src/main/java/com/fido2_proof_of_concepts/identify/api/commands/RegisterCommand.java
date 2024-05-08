package com.iwmnetwork.aqtos.internship.identify.api.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Register command.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand extends AbstractCommand {
    private String name;
    private String surname;
    private String username;
    private String password;
}
