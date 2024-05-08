package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Verify that the received rpId hash is the same as our rpId hash command.
 */
public class VerifyRpIdHashInAuthDataCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param registrationCeremonyId registration ceremony id for registration ceremony aggregate.
     */
    public VerifyRpIdHashInAuthDataCommand(Identifier registrationCeremonyId) {
        super(registrationCeremonyId);
    }
}
