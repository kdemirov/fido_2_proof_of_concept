package com.iwmnetwork.aqtos.internship.discussion.api.commands;

import com.iwmnetwork.aqtos.internship.discussion.model.Mention;
import com.iwmnetwork.aqtos.internship.discussion.model.Reference;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.TaskId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

/**
 * Update comment from a discussion command.
 */
@AllArgsConstructor
@Getter
public class UpdateCommentCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private DiscussionId discussionId;
    private CommentId commentId;
    private String newBody;
    List<Mention> mentions;
    List<Reference<TaskId>> taskReferences;
}
