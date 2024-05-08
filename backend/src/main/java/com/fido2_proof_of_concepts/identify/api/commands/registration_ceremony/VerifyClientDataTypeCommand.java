package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;

/**
 * Registration ceremony verify client data type command.
 */
public class VerifyClientDataTypeCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for aggregate registration ceremony
     */
    public VerifyClientDataTypeCommand(Identifier id) {
        super(id);
    }
}
