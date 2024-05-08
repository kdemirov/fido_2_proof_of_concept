package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Verify that user verified in auth data flags is set command.
 */
public class AuthenticationVerifyThatUserVerifiedInAuthDataIsSetCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate
     */
    public AuthenticationVerifyThatUserVerifiedInAuthDataIsSetCommand(Identifier id) {
        super(id);
    }
}
