package com.iwmnetwork.aqtos.identify.demo.api.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand extends AbstractCommand {
    private String name;
    private String surname;
    private String username;
    private String password;
}
