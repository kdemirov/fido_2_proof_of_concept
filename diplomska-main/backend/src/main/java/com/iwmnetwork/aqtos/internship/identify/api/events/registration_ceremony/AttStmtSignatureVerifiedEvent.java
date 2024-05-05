package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Attestation statement signature verified event.
 */
@Getter
public class AttStmtSignatureVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate
     * @param verified verified if the signature is valid over the concatenation of
     *                 auth data hash and client data hash
     */
    public AttStmtSignatureVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
