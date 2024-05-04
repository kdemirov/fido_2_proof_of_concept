package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.Getter;

/**
 * User verified flag is set in auth data flags event.
 */
@Getter
public class UserVerifiedInAuthDataIsSetEvent extends AbstractRegistrationCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate
     * @param verified true if the second bit in flags is set
     */
    public UserVerifiedInAuthDataIsSetEvent(RegistrationCeremonyId id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
