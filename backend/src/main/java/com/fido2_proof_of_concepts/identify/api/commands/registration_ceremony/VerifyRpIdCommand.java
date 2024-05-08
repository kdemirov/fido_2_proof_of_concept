package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.CollectedClientData;

/**
 * Registration ceremony verify that our Relying Party id is equal with the one
 * received from {@link CollectedClientData}
 */
public class VerifyRpIdCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyRpIdCommand(Identifier id) {
        super(id);
    }
}
