package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User present in auth data flags verified event.
 */
@Getter
public class UserPresentInAuthDataVerifiedEvent extends AbstractRegistrationCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate
     * @param verified true if user flag is set in auth data flags
     */
    public UserPresentInAuthDataVerifiedEvent(RegistrationCeremonyId id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
