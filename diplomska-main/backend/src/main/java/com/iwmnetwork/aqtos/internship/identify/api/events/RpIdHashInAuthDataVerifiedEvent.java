package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.Getter;

/**
 * RP id hash verified with our RP id hash event
 */
@Getter
public class RpIdHashInAuthDataVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param registrationCeremonyId registration ceremony id for registration ceremony aggregate
     * @param verified               true if received rpId hash from auth data matches our rpId hash
     */
    public RpIdHashInAuthDataVerifiedEvent(Identifier registrationCeremonyId, boolean verified) {
        super(registrationCeremonyId);
        this.verified = verified;
    }
}
