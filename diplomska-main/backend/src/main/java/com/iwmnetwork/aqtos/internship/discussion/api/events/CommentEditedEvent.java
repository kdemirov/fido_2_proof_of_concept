package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Getter;

/**
 * Comment edited event.
 */
@Getter
public class CommentEditedEvent extends DiscussionEvent {
    private CommentId commentId;
    private String newBody;

    /**
     * Constructor.
     *
     * @param discussionId discussion id for a discussion aggregate
     * @param commentId    comment id
     * @param newBody      new content of the comment
     */
    public CommentEditedEvent(DiscussionId discussionId,
                              CommentId commentId,
                              String newBody) {
        super(discussionId);
        this.commentId = commentId;
        this.newBody = newBody;
    }
}
