package com.fido2_proof_of_concepts.discussion.model.identifiers;


import com.fido2_proof_of_concepts.common.identifiers.Identifier;

/**
 * TaskId identifier
 */
public class TaskId extends Identifier {

    /**
     * Constructor creating an id.
     */
    public TaskId() {
        super();
    }

    /**
     * Constructor recreation the id
     *
     * @param id given id string
     */
    public TaskId(String id) {
        super(id);
    }
}
