package com.fido2_proof_of_concepts.discussion.api.commands;

import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
import com.fido2_proof_of_concepts.discussion.model.identifiers.CommentId;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Remove comment command.
 */
@AllArgsConstructor
@Getter
public class RemoveCommentCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private DiscussionId discussionId;
    private CommentId commentId;
}
