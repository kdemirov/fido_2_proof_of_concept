package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;

/**
 * Verify that user is present in authenticator data flags command.
 */
public class VerifyThatUserPresentInAuthDataIsSetCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyThatUserPresentInAuthDataIsSetCommand(Identifier id) {
        super(id);
    }
}
