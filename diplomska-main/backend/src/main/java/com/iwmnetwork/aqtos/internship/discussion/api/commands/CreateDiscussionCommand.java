package com.iwmnetwork.aqtos.internship.discussion.api.commands;


import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class CreateDiscussionCommand extends AbstractCommand {
    private String name;
    private String description;
    private PersonId creator;

    public CreateDiscussionCommand(@NotEmpty String name,
                                   String description,
                                   PersonId creator) {
        super();
        this.name = name;
        this.description = description;
        this.creator = creator;
    }

    public CreateDiscussionCommand() {
    }
}
