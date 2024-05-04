package com.iwmnetwork.aqtos.internship.identify.model.identifiers;

import javax.persistence.Embeddable;

@Embeddable
public class RegistrationCeremonyId extends Identifier {
    public RegistrationCeremonyId() {
    }

    public RegistrationCeremonyId(String id) {
        super(id);
    }
}
