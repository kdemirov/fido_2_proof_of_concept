package com.fido2_proof_of_concepts.discussion.model;

import com.fido2_proof_of_concepts.discussion.model.identifiers.PersonId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Mention.
 */
@AllArgsConstructor
@Getter
public class Mention {
    private PersonId personId;
    private String label;
}
