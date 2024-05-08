package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

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
