package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.enumerations;

public enum UserVerificationRequirements {
    DISCOURAGED("discouraged"),
    PREFERRED("preferred"),
    REQUIRED("required");

    public final String requirement;

    UserVerificationRequirements(String requirement) {
        this.requirement = requirement;
    }
}
