package com.fido2_proof_of_concepts.identify.api.events.registration_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.events.AbstractCeremonyEvent;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.CollectedClientData;
import lombok.Getter;


/**
 * Client data decoded event.
 */
@Getter
public class ClientDataDecodedEvent extends AbstractCeremonyEvent {

    private final CollectedClientData clientData;

    /**
     * Constructor.
     *
     * @param id         registration ceremony id for registration ceremony aggregate
     * @param clientData decoded client data
     */
    public ClientDataDecodedEvent(Identifier id, CollectedClientData clientData) {
        super(id);
        this.clientData = clientData;
    }
}
