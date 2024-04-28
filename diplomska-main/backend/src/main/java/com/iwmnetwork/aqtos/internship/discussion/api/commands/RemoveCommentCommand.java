package com.iwmnetwork.aqtos.internship.discussion.api.commands;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
public class RemoveCommentCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private DiscussionId discussionId;
    private CommentId commentId;


    public RemoveCommentCommand(DiscussionId discussionId, CommentId commentId) {
        super();
        this.discussionId = discussionId;
        this.commentId = commentId;
    }
}
