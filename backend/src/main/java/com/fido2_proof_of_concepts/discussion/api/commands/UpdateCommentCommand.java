package com.fido2_proof_of_concepts.discussion.api.commands;


import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
import com.fido2_proof_of_concepts.discussion.model.Mention;
import com.fido2_proof_of_concepts.discussion.model.Reference;
import com.fido2_proof_of_concepts.discussion.model.identifiers.CommentId;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import com.fido2_proof_of_concepts.discussion.model.identifiers.TaskId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

/**
 * Update comment from a discussion command.
 */
@NoArgsConstructor
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
