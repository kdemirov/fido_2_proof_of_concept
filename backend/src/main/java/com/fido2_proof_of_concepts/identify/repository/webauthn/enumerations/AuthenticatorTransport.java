package com.fido2_proof_of_concepts.identify.repository.webauthn.enumerations;

/**
 * Authenticator Transport.
 */
public enum AuthenticatorTransport {
    USB("usb"),
    NFC("nfc"),
    BLE("ble"),
    INTERNAL("internal");

    public final String transportType;

    /**
     * Constructor.
     *
     * @param transportType transportType
     */
    AuthenticatorTransport(String transportType) {
        this.transportType = transportType;
    }
}
