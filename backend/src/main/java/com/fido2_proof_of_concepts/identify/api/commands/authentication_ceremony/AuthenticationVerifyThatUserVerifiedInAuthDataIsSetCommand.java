package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;

/**
 * Verify that user verified in auth data flags is set command.
 */
public class AuthenticationVerifyThatUserVerifiedInAuthDataIsSetCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate
     */
    public AuthenticationVerifyThatUserVerifiedInAuthDataIsSetCommand(Identifier id) {
        super(id);
    }
}
