package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;
import lombok.AllArgsConstructor;
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
