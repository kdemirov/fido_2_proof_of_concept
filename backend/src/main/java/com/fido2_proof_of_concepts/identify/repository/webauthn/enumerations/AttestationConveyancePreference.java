package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

/**
 * Attestation conveyance preference enum.
 */
public enum AttestationConveyancePreference {

    NONE("none"),
    INDIRECT("indirect"),
    DIRECT("direct"),
    ENTERPRISE("enterprise");

    public final String attestationConveyancePreference;

    /**
     * Constructor
     *
     * @param acp given attestation conveyance preference.
     */
    AttestationConveyancePreference(String acp) {
        this.attestationConveyancePreference = acp;
    }
}
