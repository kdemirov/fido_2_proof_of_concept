package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Verify that user is present is authenticator data flags command.
 */
public class VerifyThatUserPresentInAuthDataIsSetCommand extends AbstractRegistrationCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public VerifyThatUserPresentInAuthDataIsSetCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
