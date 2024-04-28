package com.iwmnetwork.aqtos.internship.discussion.model.identifiers;

import com.iwmnetwork.aqtos.internship.discussion.model.Identifier;

import javax.persistence.Embeddable;




@Embeddable
public class CommentId extends Identifier {

    public CommentId() {
        super();
    }

    public CommentId(String id) {
        super(id);
    }

}
