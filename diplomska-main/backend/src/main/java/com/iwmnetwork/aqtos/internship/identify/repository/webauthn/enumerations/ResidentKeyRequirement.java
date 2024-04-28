package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

public enum ResidentKeyRequirement {
    DISCOURAGED("discouraged"),
    PREFERRED("preferred"),
    REQUIRED("required");

    public final String requirement;

    ResidentKeyRequirement(String requirement) {
        this.requirement = requirement;
    }
}
