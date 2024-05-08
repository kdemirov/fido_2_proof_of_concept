package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

/**
 * Public key descriptor enum.
 */
public enum PublicKeyDescriptor {
    KEY_TYPE(1),
    ALG(3),
    CURVE_TYPE(-1),
    X_COORDINATE(-2),
    Y_COORDINATE(-3);

    public final int descriptor;

    /**
     * Constructor.
     *
     * @param descriptor descriptor
     */
    PublicKeyDescriptor(int descriptor) {
        this.descriptor = descriptor;
    }
}
