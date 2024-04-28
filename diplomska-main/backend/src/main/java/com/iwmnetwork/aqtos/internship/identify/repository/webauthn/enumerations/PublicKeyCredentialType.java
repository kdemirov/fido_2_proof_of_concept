package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

public enum PublicKeyCredentialType {
    PUBLIC_KEY("public-key");

    public final String type;

    PublicKeyCredentialType(String type) {
        this.type = type;
    }
}
