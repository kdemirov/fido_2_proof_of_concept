package com.fido2_proof_of_concepts.identify.api.events.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.events.AbstractCeremonyEvent;
import com.fido2_proof_of_concepts.identify.model.FidoUser;
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
