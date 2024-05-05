package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Verify that user verified in auth data flags is set command.
 */
public class VerifyThatUserVerifiedInAuthDataIsSetCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyThatUserVerifiedInAuthDataIsSetCommand(Identifier id) {
        super(id);
    }
}
