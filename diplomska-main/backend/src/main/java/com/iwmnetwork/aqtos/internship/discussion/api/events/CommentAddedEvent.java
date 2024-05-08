package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Getter;

/**
 * Comment added to a discussion event.
 */
@Getter
public class CommentAddedEvent extends DiscussionEvent {

    private CommentId commentId;
    private PersonId author;
    private String body;

    /**
     * Constructor.
     *
     * @param discussionId discussion id for discussion aggregate
     * @param commentId    commentId
     * @param author       author's id
     * @param body         content of the comment
     */
    public CommentAddedEvent(DiscussionId discussionId,
                             CommentId commentId,
                             PersonId author,
                             String body) {
        super(discussionId);
        this.commentId = commentId;
        this.author = author;
        this.body = body;
    }

}
