package com.iwmnetwork.aqtos.identify.demo.model.identifiers;

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
