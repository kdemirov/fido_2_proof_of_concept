package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Challenge verified event for registration ceremony.
 */
@Getter
public class RegistrationChallengeVerifiedEvent extends AbstractRegistrationCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate
     * @param verified true if received challenge is the same as the one we send
     *                 in public key credentials options
     */
    public RegistrationChallengeVerifiedEvent(RegistrationCeremonyId id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
