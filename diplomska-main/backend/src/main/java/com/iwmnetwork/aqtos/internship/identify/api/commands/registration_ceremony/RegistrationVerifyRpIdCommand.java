package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Registration ceremony verify that our Relying Party id is equal with the one
 * received from {@link CollectedClientData}
 */
public class RegistrationVerifyRpIdCommand extends AbstractRegistrationCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public RegistrationVerifyRpIdCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
