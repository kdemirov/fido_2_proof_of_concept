package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Compute Hash Over Received Client Data command;
 */
@AllArgsConstructor
@Getter
public class ComputeHashOverClientDataJsonCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param registrationCeremonyId command for registration ceremony aggregate with the given id.
     */
    public ComputeHashOverClientDataJsonCommand(Identifier registrationCeremonyId) {
        super(registrationCeremonyId);
    }
}
