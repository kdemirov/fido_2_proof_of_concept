package com.iwmnetwork.aqtos.internship.discussion.api.commands;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddMemberCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private DiscussionId discussionId;
    private PersonId member;

    public AddMemberCommand(DiscussionId discussionId, PersonId member) {
        this.discussionId = discussionId;
        this.member = member;
    }
}
