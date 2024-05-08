package com.iwmnetwork.aqtos.internship.discussion.api.commands;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Remove member from a discussion command.
 */
@AllArgsConstructor
@Getter
public class RemoveMemberCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private DiscussionId discussionId;
    private PersonId member;
}
