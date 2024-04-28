package com.iwmnetwork.aqtos.internship.identify.model.identifiers;

import javax.persistence.Embeddable;

@Embeddable
public class FidoUserId extends Identifier {

    public FidoUserId() {
    }

    public FidoUserId(String id) {
        super(id);
    }
}
