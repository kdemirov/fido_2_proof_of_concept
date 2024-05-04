package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Compute Hash Over Received Client Data command;
 */
@AllArgsConstructor()
@Getter
public class ComputeHashOverClientDataJsonCommand extends AbstractRegistrationCeremonyCommand {

    /**
     * Constructor.
     *
     * @param registrationCeremonyId command for registration ceremony aggregate with the given id.
     */
    public ComputeHashOverClientDataJsonCommand(RegistrationCeremonyId registrationCeremonyId) {
        super(registrationCeremonyId);
    }
}
