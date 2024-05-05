package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.Getter;

/**
 * Relying Party id verified event.
 */
@Getter
public class RpIdVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate
     * @param verified true if received rpId is the same as our rp id.
     */
    public RpIdVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
