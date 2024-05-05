package com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;
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
