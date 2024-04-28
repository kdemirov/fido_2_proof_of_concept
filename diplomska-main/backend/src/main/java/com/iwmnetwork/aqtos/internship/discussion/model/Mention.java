package com.iwmnetwork.aqtos.internship.discussion.model;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;

public class Mention {
    private PersonId personId;
    private String label;

    public Mention(PersonId personId, String label) {
        this.personId = personId;
        this.label = label;
    }
}
