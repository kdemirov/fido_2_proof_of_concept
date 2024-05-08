package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

/**
 * User verification requirements enum.
 */
public enum UserVerificationRequirements {

    DISCOURAGED("discouraged"),
    PREFERRED("preferred"),
    REQUIRED("required");

    public final String requirement;

    /**
     * Constructor.
     *
     * @param requirement requirement.
     */
    UserVerificationRequirements(String requirement) {
        this.requirement = requirement;
    }
}
