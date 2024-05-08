package com.fido2_proof_of_concepts.identify.api.events.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.events.AbstractCeremonyEvent;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.CollectedClientData;
import lombok.Getter;

/**
 * Client data deserialized event.
 */
@Getter
public class ClientDataDeserializedEvent extends AbstractCeremonyEvent {

    private final CollectedClientData clientData;

    /**
     * Constructor.
     *
     * @param authenticationCeremonyId authentication ceremony id for authentication ceremony aggregate
     * @param clientData               deserialized client data
     */
    public ClientDataDeserializedEvent(Identifier authenticationCeremonyId,
                                       CollectedClientData clientData) {
        super(authenticationCeremonyId);
        this.clientData = clientData;
    }
}
