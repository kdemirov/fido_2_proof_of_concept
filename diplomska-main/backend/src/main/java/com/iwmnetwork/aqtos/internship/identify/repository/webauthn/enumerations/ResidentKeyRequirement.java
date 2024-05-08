package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

/**
 * Resident key requirement.
 */
public enum ResidentKeyRequirement {
    DISCOURAGED("discouraged"),
    PREFERRED("preferred"),
    REQUIRED("required");

    public final String requirement;

    /**
     * Constructor.
     *
     * @param requirement requirement
     */
    ResidentKeyRequirement(String requirement) {
        this.requirement = requirement;
    }
}
