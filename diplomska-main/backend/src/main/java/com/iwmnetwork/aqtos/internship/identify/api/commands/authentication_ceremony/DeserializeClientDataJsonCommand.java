package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;

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
