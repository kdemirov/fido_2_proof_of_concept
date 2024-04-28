package com.iwmnetwork.aqtos.internship.identify.model.identifiers;

import javax.persistence.Embeddable;

@Embeddable
public class UserId extends Identifier {
    public UserId() {
        super();
    }

    public UserId(String id) {
        super(id);
    }
}
