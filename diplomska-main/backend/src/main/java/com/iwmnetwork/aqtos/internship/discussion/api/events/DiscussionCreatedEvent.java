package com.iwmnetwork.aqtos.internship.discussion.api.events;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Data;

import java.util.List;

@Data
public class DiscussionCreatedEvent extends DiscussionEvent {

    private String name;
    private String description;
    private PersonId creator;
    private List<PersonId> members;

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
