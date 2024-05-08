package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Getter;

/**
 * Member removed from a discussion event.
 */
@Getter
public class MemberRemovedEvent extends DiscussionEvent {

    private PersonId member;

    /**
     * Constructor.
     *
     * @param discussionId discussion id for a discussion aggregate
     * @param member       member's id for removal
     */
    public MemberRemovedEvent(DiscussionId discussionId, PersonId member) {
        super(discussionId);
        this.member = member;
    }
}
