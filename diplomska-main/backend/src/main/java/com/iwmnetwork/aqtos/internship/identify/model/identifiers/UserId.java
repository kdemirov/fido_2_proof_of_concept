package com.iwmnetwork.aqtos.internship.identify.model.identifiers;

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
