package com.fido2_proof_of_concepts.identify.api.commands;

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
