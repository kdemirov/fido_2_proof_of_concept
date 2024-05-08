package com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.Getter;

/**
 * Credential id exists event.
 */
@Getter
public class CredentialExistsEvent extends AbstractCeremonyEvent {

    private final boolean verified;
    private final FidoUser fidoUser;

    /**
     * Constructor.
     *
     * @param authenticationCeremonyId authentication ceremony id for authentication ceremony aggregate
     * @param verified                 true if credential id exist in our system.
     * @param fidoUser                 fidoUser
     */
    public CredentialExistsEvent(Identifier authenticationCeremonyId,
                                 boolean verified,
                                 FidoUser fidoUser) {
        super(authenticationCeremonyId);
        this.verified = verified;
        this.fidoUser = fidoUser;
    }
}
