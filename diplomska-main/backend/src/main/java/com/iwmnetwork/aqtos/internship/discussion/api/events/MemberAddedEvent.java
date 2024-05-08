package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Member added to a discussion event.
 */
@Getter
@NoArgsConstructor
public class MemberAddedEvent extends DiscussionEvent {

    private PersonId member;

    /**
     * Constructor.
     *
     * @param discussionId discussion id for a discussion aggregate
     * @param member       member's id
     */
    public MemberAddedEvent(DiscussionId discussionId, PersonId member) {
        super(discussionId);
        this.member = member;
    }
}
