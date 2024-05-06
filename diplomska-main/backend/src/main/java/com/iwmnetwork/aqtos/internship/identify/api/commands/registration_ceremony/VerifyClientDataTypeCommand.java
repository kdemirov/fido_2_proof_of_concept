package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;

/**
 * Registration ceremony verify client data type command.
 */
public class VerifyClientDataTypeCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for aggregate registration ceremony
     */
    public VerifyClientDataTypeCommand(Identifier id) {
        super(id);
    }
}
