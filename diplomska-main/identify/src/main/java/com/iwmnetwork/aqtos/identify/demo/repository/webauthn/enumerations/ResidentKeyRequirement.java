package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.enumerations;

public enum ResidentKeyRequirement {
    DISCOURAGED("discouraged"),
    PREFERRED("preferred"),
    REQUIRED("required");

    public final String requirement;

    ResidentKeyRequirement(String requirement) {
        this.requirement = requirement;
    }
}
