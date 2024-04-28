package com.iwmnetwork.aqtos.internship.discussion.api.commands;

import com.iwmnetwork.aqtos.internship.discussion.model.*;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.TaskId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
public class UpdateCommentCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private DiscussionId discussionId;
    private CommentId commentId;
    private String newBody;
    List<Mention> mentions;
    List<Reference<TaskId>> taskReferences;

    public UpdateCommentCommand(DiscussionId discussionId,
                                CommentId commentId,
                                String newBody,
                                List<Mention> mentions,
                                List<Reference<TaskId>> taskReferences) {
        super();
        this.discussionId = discussionId;
        this.commentId = commentId;
        this.newBody = newBody;
        this.mentions = mentions;
        this.taskReferences = taskReferences;
    }

    public UpdateCommentCommand() {
    }
}
