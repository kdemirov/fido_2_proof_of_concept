package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Verify that user is present is authenticator data flags command.
 */
public class AuthenticationVerifyThatUserPresentInAuthDataIsSetCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate
     */
    public AuthenticationVerifyThatUserPresentInAuthDataIsSetCommand(Identifier id) {
        super(id);
    }
}
