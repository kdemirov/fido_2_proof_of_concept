package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;


/**
 * Verify that user is present in authenticator data flags command.
 */
public class AuthenticationVerifyThatUserPresentInAuthDataIsSetCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate
     */
    public AuthenticationVerifyThatUserPresentInAuthDataIsSetCommand(Identifier id) {
        super(id);
    }
}
