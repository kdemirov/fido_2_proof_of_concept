package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Data;


@Data
public class CommentEditedEvent extends DiscussionEvent {
    private CommentId commentId;
    private String newBody;


    public CommentEditedEvent(DiscussionId discussionId,
                              CommentId commentId,
                              String newBody) {
        super(discussionId);
        this.commentId = commentId;
        this.newBody = newBody;
    }
}
