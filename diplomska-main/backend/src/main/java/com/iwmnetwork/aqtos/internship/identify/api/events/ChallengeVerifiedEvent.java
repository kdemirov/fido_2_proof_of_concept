package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.Getter;

/**
 * Challenge verified event for both ceremonies'.
 */
@Getter
public class ChallengeVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       ceremony id for ceremony aggregate
     * @param verified true if received challenge is the same as the one we send
     *                 in public key credentials options or public key request options
     */
    public ChallengeVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
