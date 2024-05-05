package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.Getter;

/**
 * Client data type verified for registration ceremony event.
 */
@Getter
public class ClientDataTypeVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate.
     * @param verified true if client data is webauthn.create.
     */
    public ClientDataTypeVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
