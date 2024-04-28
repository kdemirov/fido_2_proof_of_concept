package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

public enum AuthenticatorAttachments {
    CROSS_PLATFORM("cross-platform"),
    PLATFORM("platform");

    public final String platform;

    AuthenticatorAttachments(String platform) {
        this.platform = platform;
    }
}
