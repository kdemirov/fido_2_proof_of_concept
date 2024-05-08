package com.fido2_proof_of_concepts.identify.model.identifiers;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;

import javax.persistence.Embeddable;

/**
 * User id.
 */
@Embeddable
public class UserId extends Identifier {

    /**
     * Constructor for creating id.
     */
    public UserId() {
        super();
    }

    /**
     * Constructor for recreating id.
     *
     * @param id given id.
     */
    public UserId(String id) {
        super(id);
    }
}
