package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Data;

@Data
public class MemberAddedEvent extends DiscussionEvent {

    private PersonId member;

    public MemberAddedEvent(DiscussionId discussionId, PersonId member) {
        super(discussionId);
        this.member = member;
    }

    public MemberAddedEvent() {
    }
}
