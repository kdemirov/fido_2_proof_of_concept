package com.iwmnetwork.aqtos.internship.discussion.api.commands;

import com.iwmnetwork.aqtos.internship.discussion.model.Mention;
import com.iwmnetwork.aqtos.internship.discussion.model.Reference;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.TaskId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Add comment to a discussion command.
 */
@NoArgsConstructor
@Getter
public class AddCommentCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private DiscussionId discussionId;
    private PersonId author;
    private String body;
    private List<Mention> mentions;
    private List<Reference<TaskId>> taskReferences;

    /**
     * Constructor.
     *
     * @param discussionId   discussion id for discussion aggregate
     * @param author         author's id of the comment
     * @param body           content of the comment
     * @param mentions       mentions
     * @param taskReferences task references
     */
    public AddCommentCommand(DiscussionId discussionId,
                             PersonId author,
                             @NotEmpty String body,
                             List<Mention> mentions,
                             List<Reference<TaskId>> taskReferences) {
        this.discussionId = discussionId;
        this.author = author;
        this.body = body;
        this.mentions = mentions;
        this.taskReferences = taskReferences;
    }
}
