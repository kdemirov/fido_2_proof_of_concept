package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Verify the received AttestationStatement signature over the concatenation
 * of auth data hash and client data hash command.
 */
public class VerifyAttStmtSignatureCommand extends AbstractRegistrationCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyAttStmtSignatureCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
