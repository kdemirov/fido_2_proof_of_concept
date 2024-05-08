package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

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
