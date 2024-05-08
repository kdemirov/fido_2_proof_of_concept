package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.RoutingKey;

/**
 * Create public key credential request options command.
 */
@AllArgsConstructor
@Getter
public class CreatePublicKeyCredentialRequestOptionsCommand extends AbstractCommand {

    @RoutingKey
    private AuthenticationCeremonyId authenticationCeremonyId;
}
