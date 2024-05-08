package com.fido2_proof_of_concepts.identify.api.commands;

import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Register fido user with the given attributes if all steps passed successfully command.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FidoUserRegistrationFinishCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private UserId userId;
    private String name;
    private String username;
    private byte[] publicKey;
    private byte[] credentialId;
    private int signCount;
}
