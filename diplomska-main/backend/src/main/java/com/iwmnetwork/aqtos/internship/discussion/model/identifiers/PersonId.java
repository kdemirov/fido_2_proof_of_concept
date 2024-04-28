package com.iwmnetwork.aqtos.internship.discussion.model.identifiers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.iwmnetwork.aqtos.internship.discussion.model.Identifier;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PersonId extends Identifier {

    public PersonId() {
        super();
    }

    public PersonId(String id) {
        super(id);
    }

}
