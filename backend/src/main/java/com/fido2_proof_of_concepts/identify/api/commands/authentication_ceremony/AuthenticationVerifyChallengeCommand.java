package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Authentication Ceremony Verify Challenge Command.
 */
public class AuthenticationVerifyChallengeCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate.
     */
    public AuthenticationVerifyChallengeCommand(Identifier id) {
        super(id);
    }
}
