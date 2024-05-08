package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Compute Hash Over Received Client Data command;
 */
@AllArgsConstructor
@Getter
public class AuthenticationComputeHashOverClientDataJsonCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param authenticationId command for authentication ceremony aggregate with the given id.
     */
    public AuthenticationComputeHashOverClientDataJsonCommand(Identifier authenticationId) {
        super(authenticationId);
    }
}
