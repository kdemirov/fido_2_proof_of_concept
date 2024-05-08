package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Verify Authenticator Assertion Response signature.
 */
public class VerifyAssertionResponseSignatureCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id authentication ceremony id for authentication ceremony aggregate.
     */
    public VerifyAssertionResponseSignatureCommand(Identifier id) {
        super(id);
    }
}
