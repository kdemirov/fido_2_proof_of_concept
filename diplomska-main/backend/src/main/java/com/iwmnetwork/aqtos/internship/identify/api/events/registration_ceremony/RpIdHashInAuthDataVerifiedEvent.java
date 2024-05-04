package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RP id hash verified with our RP id hash event
 */
@Getter
public class RpIdHashInAuthDataVerifiedEvent extends AbstractRegistrationCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param registrationCeremonyId registration ceremony id for registration ceremony aggregate
     * @param verified               true if received rpId hash from auth data matches our rpId hash
     */
    public RpIdHashInAuthDataVerifiedEvent(RegistrationCeremonyId registrationCeremonyId, boolean verified) {
        super(registrationCeremonyId);
        this.verified = verified;
    }
}
