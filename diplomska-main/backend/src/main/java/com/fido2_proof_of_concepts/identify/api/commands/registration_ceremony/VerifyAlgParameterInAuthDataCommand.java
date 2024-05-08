package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;

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
