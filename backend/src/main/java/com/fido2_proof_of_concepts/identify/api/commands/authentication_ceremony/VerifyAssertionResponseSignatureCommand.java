package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;


import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;

/**
 * Verify Authenticator Assertion Response signature.
 */
public class VerifyAssertionResponseSignatureCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate.
     */
    public VerifyAssertionResponseSignatureCommand(Identifier id) {
        super(id);
    }
}
