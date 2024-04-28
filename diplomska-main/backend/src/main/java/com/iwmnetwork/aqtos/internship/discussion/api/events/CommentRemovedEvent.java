package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Data;

@Data
public class CommentRemovedEvent extends DiscussionEvent {

    private CommentId commentId;


    public CommentRemovedEvent(DiscussionId discussionId, CommentId commentId) {
        super(discussionId);
        this.commentId = commentId;
    }
}
