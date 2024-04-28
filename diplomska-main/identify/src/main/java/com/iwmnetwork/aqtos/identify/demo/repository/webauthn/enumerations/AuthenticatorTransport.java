package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.enumerations;

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
