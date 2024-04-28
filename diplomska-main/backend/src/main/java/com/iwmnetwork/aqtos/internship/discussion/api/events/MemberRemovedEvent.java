package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Data;

@Data
public class MemberRemovedEvent extends DiscussionEvent {

    private PersonId member;

    public MemberRemovedEvent(DiscussionId discussionId, PersonId member) {
        super(discussionId);
        this.member = member;
    }

    public MemberRemovedEvent() {
    }
}
