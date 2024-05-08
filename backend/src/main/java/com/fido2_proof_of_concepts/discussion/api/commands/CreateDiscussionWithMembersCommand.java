package com.fido2_proof_of_concepts.discussion.api.commands;

import com.fido2_proof_of_concepts.discussion.model.identifiers.PersonId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Creates discussion with members command.
 */
@NoArgsConstructor
@Getter
public class CreateDiscussionWithMembersCommand extends CreateDiscussionCommand {

    private List<PersonId> members;

    /**
     * Constructor.
     *
     * @param name        name of the discussion.
     * @param description description of the discussion
     * @param creator     creator's id of the discussion
     * @param members     list of members id of the discussion.
     */
    public CreateDiscussionWithMembersCommand(String name,
                                              String description,
                                              PersonId creator,
                                              List<PersonId> members) {
        super(name, description, creator);
        this.members = members;
    }
}
