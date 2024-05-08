package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;


import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;

/**
 * Deserialize client data json command.
 */
public class DeserializeClientDataJsonCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param authenticationCeremonyId authenticator ceremony id for authentication ceremony aggregate
     */
    public DeserializeClientDataJsonCommand(AuthenticationCeremonyId authenticationCeremonyId) {
        super(authenticationCeremonyId);
    }
}
