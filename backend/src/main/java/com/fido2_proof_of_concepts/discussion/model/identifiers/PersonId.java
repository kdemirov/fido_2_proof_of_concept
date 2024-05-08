package com.fido2_proof_of_concepts.discussion.model.identifiers;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;

import javax.persistence.Embeddable;

/**
 * Person id.
 */
@Embeddable
public class PersonId extends Identifier {

    /**
     * Constructor for creating an id.
     */
    public PersonId() {
        super();
    }

    /**
     * Constructor for recreation the id.
     *
     * @param id given id
     */
    public PersonId(String id) {
        super(id);
    }

}
