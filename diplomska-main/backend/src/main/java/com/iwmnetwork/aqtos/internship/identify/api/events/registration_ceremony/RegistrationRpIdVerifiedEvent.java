package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.RegistrationVerifyRpIdCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Relying Party id verified event.
 */
@Getter
public class RegistrationRpIdVerifiedEvent extends AbstractRegistrationCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate
     * @param verified true if received rpId is the same as our rp id.
     */
    public RegistrationRpIdVerifiedEvent(RegistrationCeremonyId id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
