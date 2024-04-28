package com.iwmnetwork.aqtos.internship.discussion.model.identifiers;

import com.iwmnetwork.aqtos.internship.discussion.model.Identifier;

import javax.persistence.Embeddable;

@Embeddable
public class DiscussionId extends Identifier {
    public DiscussionId() {
        super();
    }

    public DiscussionId(String id) {
        super(id);
    }

}
