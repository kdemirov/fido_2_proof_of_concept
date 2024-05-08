package com.iwmnetwork.aqtos.internship.discussion.model.identifiers;

import com.iwmnetwork.aqtos.internship.discussion.model.Identifier;

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
