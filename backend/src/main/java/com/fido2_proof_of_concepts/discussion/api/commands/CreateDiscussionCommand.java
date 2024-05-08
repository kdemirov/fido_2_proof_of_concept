package com.fido2_proof_of_concepts.discussion.api.commands;

import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
import com.fido2_proof_of_concepts.discussion.model.identifiers.PersonId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Create discussion command.
 */
@NoArgsConstructor
@Getter
public class CreateDiscussionCommand extends AbstractCommand {

    private String name;
    private String description;
    private PersonId creator;

    /**
     * Constructor.
     *
     * @param name        discussion name
     * @param description discussion description
     * @param creator     creator's id of the discussion
     */
    public CreateDiscussionCommand(@NotEmpty String name,
                                   String description,
                                   PersonId creator) {
        super();
        this.name = name;
        this.description = description;
        this.creator = creator;
    }
}
