package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.RoutingKey;

/**
 * CreatePublicKeyCredentialsOptionsCommand.
 */
@AllArgsConstructor
@Getter
public class CreatePublicKeyCredentialsOptionsCommand extends AbstractCommand {
    @RoutingKey
    private RegistrationCeremonyId id;
    private String username;
    private String challenge;
}
