package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.Getter;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;

/**
 * Decode Json Client Data Command.
 */
@Getter
public class DecodeJsonClientDataCommand extends AbstractCeremonyCommand {

    private final String clientDataJson;

    /**
     * Constructor.
     *
     * @param id             id for registration ceremony aggregate.
     * @param clientDataJson clientDataJson for deserializing in our model {@link CollectedClientData}
     */
    public DecodeJsonClientDataCommand(RegistrationCeremonyId id, String clientDataJson) {
        super(id);
        this.clientDataJson = clientDataJson;
    }
}
