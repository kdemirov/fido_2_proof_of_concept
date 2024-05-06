package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Verify that the received rpId hash is the same as our rpId hash command.
 */
public class AuthenticationVerifyRpIdHashInAuthDataCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param authenticationId authentication  ceremony id for authentication ceremony aggregate.
     */
    public AuthenticationVerifyRpIdHashInAuthDataCommand(Identifier authenticationId) {
        super(authenticationId);
    }
}
