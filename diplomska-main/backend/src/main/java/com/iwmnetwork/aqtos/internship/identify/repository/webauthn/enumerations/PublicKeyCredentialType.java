package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

/**
 * Public Key Credential Type enum.
 */
public enum PublicKeyCredentialType {
    PUBLIC_KEY("public-key");

    public final String type;

    /**
     * Constructor.
     *
     * @param type type
     */
    PublicKeyCredentialType(String type) {
        this.type = type;
    }
}
