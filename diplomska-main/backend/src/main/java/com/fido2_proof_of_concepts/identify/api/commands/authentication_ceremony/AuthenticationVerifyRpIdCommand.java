package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;

/**
 * Authentication ceremony verify that our Relying Party id is equal with the one
 * received from {@link CollectedClientData}
 */
public class AuthenticationVerifyRpIdCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate
     */
    public AuthenticationVerifyRpIdCommand(Identifier id) {
        super(id);
    }
}
