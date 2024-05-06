package com.iwmnetwork.aqtos.internship.discussion.model.identifiers;

import com.iwmnetwork.aqtos.internship.discussion.model.Identifier;
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
