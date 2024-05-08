package com.fido2_proof_of_concepts.identify.api.commands;

import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
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
