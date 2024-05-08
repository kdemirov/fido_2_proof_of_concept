package com.iwmnetwork.aqtos.internship.identify.model.identifiers;

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
