package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Alg in auth data verified event.
 */
@Getter
public class AlgParameterInAuthDataVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate.
     * @param verified true if alg matches with the one we send in CreatePublicKeyCredentialsOptions
     */
    public AlgParameterInAuthDataVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
