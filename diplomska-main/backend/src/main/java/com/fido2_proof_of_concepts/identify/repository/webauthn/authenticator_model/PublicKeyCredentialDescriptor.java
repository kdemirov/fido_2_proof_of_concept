/*
this object is part of PublicKeyCredentialCreationObject
https://w3c.github.io/webauthn/#dictdef-publickeycredentialdescriptor
* */
package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model;

import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations.AuthenticatorTransport;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PublicKeyCredentialDescriptor {
    private String type;
    private String id;
    private String[] transports;

    public PublicKeyCredentialDescriptor(String type, String id) {
        this.type = type;
        this.id = id;
        String[] strings = new String[1];
        strings[0] = AuthenticatorTransport.BLE.transportType;
        this.transports = strings;
    }
}
