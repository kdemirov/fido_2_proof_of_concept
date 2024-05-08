package com.iwmnetwork.aqtos.internship.identify.model.identifiers;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Authentication Ceremony Id.
 */
@NoArgsConstructor
@Embeddable
public class AuthenticationCeremonyId extends Identifier {

    /**
     * Constructor.
     *
     * @param id given id
     */
    public AuthenticationCeremonyId(String id) {
        super(id);
    }
}
