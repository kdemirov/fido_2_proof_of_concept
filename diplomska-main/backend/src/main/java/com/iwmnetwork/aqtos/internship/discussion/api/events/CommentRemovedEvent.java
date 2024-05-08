package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Getter;

/**
 * Comment removed event.
 */
@Getter
public class CommentRemovedEvent extends DiscussionEvent {

    private CommentId commentId;

    /**
     * Constructor.
     *
     * @param discussionId discussion id for a discussion aggregate
     * @param commentId    comment id for removal
     */
    public CommentRemovedEvent(DiscussionId discussionId, CommentId commentId) {
        super(discussionId);
        this.commentId = commentId;
    }
}
