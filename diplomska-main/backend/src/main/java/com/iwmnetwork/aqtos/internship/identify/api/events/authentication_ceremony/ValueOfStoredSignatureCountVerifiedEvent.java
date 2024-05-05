package com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.Getter;

/**
 * Value of stored signature count verified event
 */
@Getter
public class ValueOfStoredSignatureCountVerifiedEvent extends AbstractCeremonyEvent {

    private boolean verified;
    private int signatureCount;

    public ValueOfStoredSignatureCountVerifiedEvent(Identifier ceremonyId,
                                                    boolean verified,
                                                    int signatureCount) {
        super(ceremonyId);
        this.verified = verified;
        this.signatureCount = signatureCount;
    }
}
