package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations;

public enum AuthenticatorTransport {
    USB("usb"),
    NFC("nfc"),
    BLE("ble"),
    INTERNAL("internal");

    public final String transportType;

    AuthenticatorTransport(String transportType) {
        this.transportType = transportType;
    }
}
