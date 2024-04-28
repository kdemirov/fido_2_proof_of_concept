package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

public enum PublicKeyDescriptor {
    KEY_TYPE(1),
    ALG(3),
    CURVE_TYPE(-1),
    X_COORDINATE(-2),
    Y_COORDINATE(-3);

    public final int descriptor;

    PublicKeyDescriptor(int descriptor) {
        this.descriptor = descriptor;
    }
}
