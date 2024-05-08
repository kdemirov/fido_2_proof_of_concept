package com.iwmnetwork.aqtos.internship.discussion.model.identifiers;

import com.iwmnetwork.aqtos.internship.discussion.model.Identifier;

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
