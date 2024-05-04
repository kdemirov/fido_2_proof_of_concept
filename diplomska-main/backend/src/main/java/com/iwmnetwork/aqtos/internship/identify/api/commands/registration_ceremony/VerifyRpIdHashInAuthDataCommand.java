package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Verify that the received rpId hash is the same as our rpId hash command.
 */
public class VerifyRpIdHashInAuthDataCommand extends AbstractRegistrationCeremonyCommand {

    /**
     * Constructor.
     *
     * @param registrationCeremonyId registration ceremony id for registration ceremony aggregate.
     */
    public VerifyRpIdHashInAuthDataCommand(RegistrationCeremonyId registrationCeremonyId) {
        super(registrationCeremonyId);
    }
}
