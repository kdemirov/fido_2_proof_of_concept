package com.fido2_proof_of_concepts.discussion.api.events;

import com.fido2_proof_of_concepts.common.events.AbstractEvent;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Abstract class for all discussion events related to discussion aggregate.
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
