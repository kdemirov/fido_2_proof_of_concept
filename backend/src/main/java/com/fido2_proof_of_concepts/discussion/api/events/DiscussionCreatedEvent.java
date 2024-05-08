package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Getter;

import java.util.List;

/**
 * Discussion created event.
 */
@Getter
public class DiscussionCreatedEvent extends DiscussionEvent {

    private String name;
    private String description;
    private PersonId creator;
    private List<PersonId> members;

    /**
     * Constructor.
     *
     * @param discussionId discussion id for a discussion aggregate
     * @param name         discussion name
     * @param description  discussion description
     * @param creator      discussion creator
     * @param members      discussion members
     */
    public DiscussionCreatedEvent(DiscussionId discussionId,
                                  String name,
                                  String description,
                                  PersonId creator,
                                  List<PersonId> members) {
        super(discussionId);
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.members = members;
    }
}
