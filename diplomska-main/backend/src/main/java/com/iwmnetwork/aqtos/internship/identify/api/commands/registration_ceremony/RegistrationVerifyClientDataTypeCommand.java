package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;

/**
 * Registration ceremony verify client data type command.
 */
public class RegistrationVerifyClientDataTypeCommand extends AbstractRegistrationCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for aggregate registration ceremony
     */
    public RegistrationVerifyClientDataTypeCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
