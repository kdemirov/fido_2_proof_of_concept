package com.fido2_proof_of_concepts.identify.model.identifiers;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;

import javax.persistence.Embeddable;

/**
 * Registration Ceremony id.
 */
@Embeddable
public class RegistrationCeremonyId extends Identifier {

    /**
     * Constructor for creating an id.
     */
    public RegistrationCeremonyId() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id given id
     */
    public RegistrationCeremonyId(String id) {
        super(id);
    }
}
