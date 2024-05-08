package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;

/**
 * Verify that the received rpId hash is the same as our rpId hash command.
 */
public class VerifyRpIdHashInAuthDataCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param registrationCeremonyId registration ceremony id for registration ceremony aggregate.
     */
    public VerifyRpIdHashInAuthDataCommand(Identifier registrationCeremonyId) {
        super(registrationCeremonyId);
    }
}
