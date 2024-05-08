package com.fido2_proof_of_concepts.identify.repository.webauthn.enumerations;

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
