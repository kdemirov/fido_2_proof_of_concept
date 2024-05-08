package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;

/**
 * Verify the received AttestationStatement signature over the concatenation
 * of auth data hash and client data hash command.
 */
public class VerifyAttStmtSignatureCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyAttStmtSignatureCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
