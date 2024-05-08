package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.CollectedClientData;

/**
 * Authentication ceremony verify that our Relying Party id is equal with the one
 * received from {@link CollectedClientData}
 */
public class AuthenticationVerifyRpIdCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate
     */
    public AuthenticationVerifyRpIdCommand(Identifier id) {
        super(id);
    }
}
