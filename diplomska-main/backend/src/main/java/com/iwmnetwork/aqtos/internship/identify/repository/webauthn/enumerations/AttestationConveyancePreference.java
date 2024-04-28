package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

public enum AttestationConveyancePreference {
    NONE("none"),
    INDIRECT("indirect"),
    DIRECT("direct"),
    ENTERPRISE("enterprise");

    public final String attestationConveyancePreference;

    AttestationConveyancePreference(String acp) {
        this.attestationConveyancePreference = acp;
    }
}
