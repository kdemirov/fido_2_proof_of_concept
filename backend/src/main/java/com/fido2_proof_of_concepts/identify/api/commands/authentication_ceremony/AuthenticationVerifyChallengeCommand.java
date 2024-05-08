package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;

/**
 * Authentication Ceremony Verify Challenge Command.
 */
public class AuthenticationVerifyChallengeCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate.
     */
    public AuthenticationVerifyChallengeCommand(Identifier id) {
        super(id);
    }
}
