package com.fido2_proof_of_concepts.identify.model.identifiers;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Fido user id.
 */
@NoArgsConstructor
@Embeddable
public class FidoUserId extends Identifier {

    /**
     * Constructor.
     *
     * @param id given id.
     */
    public FidoUserId(String id) {
        super(id);
    }
}
