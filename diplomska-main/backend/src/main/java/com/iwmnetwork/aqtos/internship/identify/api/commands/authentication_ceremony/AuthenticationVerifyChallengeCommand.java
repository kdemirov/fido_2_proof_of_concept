package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Registration Ceremony Verify Challenge Command.
 */
public class AuthenticationVerifyChallengeCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate.
     */
    public AuthenticationVerifyChallengeCommand(Identifier id) {
        super(id);
    }
}
