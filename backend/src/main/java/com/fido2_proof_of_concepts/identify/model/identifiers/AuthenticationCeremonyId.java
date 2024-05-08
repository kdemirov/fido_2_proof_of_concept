package com.fido2_proof_of_concepts.identify.model.identifiers;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
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
