package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;

/**
 * Authentication ceremony verify client data type command.
 */
public class AuthenticationVerifyClientDataTypeCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication id for aggregate authentication ceremony
     */
    public AuthenticationVerifyClientDataTypeCommand(Identifier id) {
        super(id);
    }
}
