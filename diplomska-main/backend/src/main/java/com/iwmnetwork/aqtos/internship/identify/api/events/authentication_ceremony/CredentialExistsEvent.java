package com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.Getter;

/**
 * Credential id exists event.
 */
@Getter
public class CredentialExistsEvent extends AbstractCeremonyEvent {

    private final boolean verified;
    private final byte[] publicKey;

    /**
     * Constructor.
     *
     * @param authenticationCeremonyId authentication ceremony id for authentication ceremony aggregate
     * @param verified                 true if credential id exist in our system.
     * @param publicKey                public key
     */
    public CredentialExistsEvent(Identifier authenticationCeremonyId,
                                 boolean verified,
                                 byte[] publicKey) {
        super(authenticationCeremonyId);
        this.verified = verified;
        this.publicKey = publicKey;
    }
}
