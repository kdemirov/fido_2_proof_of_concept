package com.fido2_proof_of_concepts.identify.repository.webauthn.enumerations;

/**
 * Types of Authentication Attachments enum.
 */
public enum AuthenticatorAttachments {
    CROSS_PLATFORM("cross-platform"),
    PLATFORM("platform");

    public final String platform;

    /**
     * Constructor.
     *
     * @param platform platform
     */
    AuthenticatorAttachments(String platform) {
        this.platform = platform;
    }
}
