package com.iwmnetwork.aqtos.internship.discussion.api.commands;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Data;

import java.util.List;

@Data
public class CreateDiscussionWithMembersCommand extends CreateDiscussionCommand {

    private List<PersonId> members;

    public CreateDiscussionWithMembersCommand(String name,
                                              String description,
                                              PersonId creator,
                                              List<PersonId> members) {
        super(name, description, creator);
        this.members = members;
    }


}
