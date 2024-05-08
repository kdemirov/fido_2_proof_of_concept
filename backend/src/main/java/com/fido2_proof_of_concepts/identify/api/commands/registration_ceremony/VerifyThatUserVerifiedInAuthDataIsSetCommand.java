package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;

/**
 * Verify that user verified flag in auth data flags is set command.
 */
public class VerifyThatUserVerifiedInAuthDataIsSetCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyThatUserVerifiedInAuthDataIsSetCommand(Identifier id) {
        super(id);
    }
}
