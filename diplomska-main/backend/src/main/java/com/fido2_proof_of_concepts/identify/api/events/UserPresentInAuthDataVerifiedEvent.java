package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.Getter;

/**
 * User present in auth data flags verified event.
 */
@Getter
public class UserPresentInAuthDataVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       ceremony id for ceremony aggregate
     * @param verified true if user flag is set in auth data flags
     */
    public UserPresentInAuthDataVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
