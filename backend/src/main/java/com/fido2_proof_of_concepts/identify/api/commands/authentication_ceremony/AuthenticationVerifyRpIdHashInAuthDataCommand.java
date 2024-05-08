package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;


/**
 * Verify that the received rpId hash is the same as our rpId hash command.
 */
public class AuthenticationVerifyRpIdHashInAuthDataCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param authenticationId authentication  ceremony id for authentication ceremony aggregate.
     */
    public AuthenticationVerifyRpIdHashInAuthDataCommand(Identifier authenticationId) {
        super(authenticationId);
    }
}
