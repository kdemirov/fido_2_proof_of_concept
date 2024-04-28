package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.enumerations;

public enum AuthenticatorAttachments {
    CROSS_PLATFORM("cross-platform"),
    PLATFORM("platform");

    public final String platform;

    AuthenticatorAttachments(String platform) {
        this.platform = platform;
    }
}
