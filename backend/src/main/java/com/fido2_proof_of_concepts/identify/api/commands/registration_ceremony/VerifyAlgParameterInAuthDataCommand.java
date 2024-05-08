package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;


import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;

/**
 * Verify alg parameter is the same as the one we send in {@link CreatePublicKeyCredentialsOptionsCommand}
 */
public class VerifyAlgParameterInAuthDataCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyAlgParameterInAuthDataCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
