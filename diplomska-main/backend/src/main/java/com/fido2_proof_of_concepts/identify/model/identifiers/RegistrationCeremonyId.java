package com.iwmnetwork.aqtos.internship.identify.model.identifiers;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Registration Ceremony id.
 */
@NoArgsConstructor
@Embeddable
public class RegistrationCeremonyId extends Identifier {

    /**
     * Constructor.
     *
     * @param id given id
     */
    public RegistrationCeremonyId(String id) {
        super(id);
    }
}
