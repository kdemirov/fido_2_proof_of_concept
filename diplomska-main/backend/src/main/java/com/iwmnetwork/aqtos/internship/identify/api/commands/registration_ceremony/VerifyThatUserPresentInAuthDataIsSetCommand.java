package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Verify that user is present in authenticator data flags command.
 */
public class VerifyThatUserPresentInAuthDataIsSetCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyThatUserPresentInAuthDataIsSetCommand(Identifier id) {
        super(id);
    }
}
