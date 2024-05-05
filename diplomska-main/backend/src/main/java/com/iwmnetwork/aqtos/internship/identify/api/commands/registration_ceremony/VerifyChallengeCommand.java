package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Registration Ceremony Verify Challenge Command.
 */
public class VerifyChallengeCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate.
     */
    public VerifyChallengeCommand(Identifier id) {
        super(id);
    }
}
