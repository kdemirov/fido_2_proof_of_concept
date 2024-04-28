package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Getter;

@Getter
public abstract class DiscussionEvent extends AbstractEvent {

    private DiscussionId discussionId;

    public DiscussionEvent(DiscussionId id) {
        super();
        this.discussionId = id;
    }

    public DiscussionEvent() {
    }
}
