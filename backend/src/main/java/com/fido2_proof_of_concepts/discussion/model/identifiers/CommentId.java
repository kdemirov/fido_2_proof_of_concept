package com.fido2_proof_of_concepts.discussion.model.identifiers;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Comment id.
 */
@NoArgsConstructor
@Embeddable
public class CommentId extends Identifier {

    /**
     * Constructor.
     *
     * @param id given id
     */
    public CommentId(String id) {
        super(id);
    }
}
