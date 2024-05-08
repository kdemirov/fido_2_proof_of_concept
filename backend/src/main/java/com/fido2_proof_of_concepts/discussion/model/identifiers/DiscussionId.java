package com.fido2_proof_of_concepts.discussion.model.identifiers;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;

import javax.persistence.Embeddable;

/**
 * Discussion id.
 */
@Embeddable
public class DiscussionId extends Identifier {

    /**
     * Constructor for creating an id.
     */
    public DiscussionId() {
        super();
    }

    /**
     * Constructor for recreation the id.
     *
     * @param id given id
     */
    public DiscussionId(String id) {
        super(id);
    }

}
