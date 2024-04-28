package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

public enum UserVerificationRequirements {
    DISCOURAGED("discouraged"),
    PREFERRED("preferred"),
    REQUIRED("required");

    public final String requirement;

    UserVerificationRequirements(String requirement) {
        this.requirement = requirement;
    }
}
