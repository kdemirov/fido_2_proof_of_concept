package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.Getter;

/**
 * Client data type verified for registration ceremony event.
 */
@Getter
public class RegistrationClientDataTypeVerifiedEvent extends AbstractRegistrationCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate.
     * @param verified true if client data is webauthn.create.
     */
    public RegistrationClientDataTypeVerifiedEvent(RegistrationCeremonyId id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
