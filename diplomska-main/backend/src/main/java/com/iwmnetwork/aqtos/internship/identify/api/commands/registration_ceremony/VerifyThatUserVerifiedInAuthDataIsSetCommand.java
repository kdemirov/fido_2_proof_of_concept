package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;

/**
 * Verify that user verified in auth data flags is set command.
 */
public class VerifyThatUserVerifiedInAuthDataIsSetCommand extends AbstractRegistrationCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyThatUserVerifiedInAuthDataIsSetCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
