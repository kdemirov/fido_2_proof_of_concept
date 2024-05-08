package com.fido2_proof_of_concepts.identify.repository.webauthn.enumerations;

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
