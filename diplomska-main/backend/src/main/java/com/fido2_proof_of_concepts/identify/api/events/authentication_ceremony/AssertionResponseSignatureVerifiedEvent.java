package com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.Getter;

/**
 * Authenticator Assertion Response signature verified event.
 */
@Getter
public class AssertionResponseSignatureVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param ceremonyId authentication ceremony id for authentication ceremony aggregate
     * @param verified   true if signature if valid otherwise false
     */
    public AssertionResponseSignatureVerifiedEvent(Identifier ceremonyId, boolean verified) {
        super(ceremonyId);
        this.verified = verified;
    }
}
