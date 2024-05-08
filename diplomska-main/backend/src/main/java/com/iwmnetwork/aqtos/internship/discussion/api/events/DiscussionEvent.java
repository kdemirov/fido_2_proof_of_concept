package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Abstract class for all discussion event related to discussion aggregate.
 */
@NoArgsConstructor
@Getter
public abstract class DiscussionEvent extends AbstractEvent {

    private DiscussionId discussionId;

    /**
     * Constructor.
     *
     * @param id discussion id for a discussion aggregate
     */
    public DiscussionEvent(DiscussionId id) {
        super();
        this.discussionId = id;
    }
}
