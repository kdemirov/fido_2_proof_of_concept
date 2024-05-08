package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;

/**
 * Verify the received AttestationStatement signature over the concatenation
 * of auth data hash and client data hash command.
 */
public class VerifyAttStmtSignatureCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyAttStmtSignatureCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
