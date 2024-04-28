/*
this object is also part of PublicKeyCredentialCreationOptions
for sending type of alg
https://w3c.github.io/webauthn/#dictdef-publickeycredentialparameters
* */
package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.authenticator_model;

import com.iwmnetwork.aqtos.identify.demo.bootstrap.Constants;
import lombok.Data;

@Data
public class PublicKeyCredentialParameters {
    private int alg;
    private String type;

    public PublicKeyCredentialParameters() {
        this.alg = Constants.COSEAlgorithmIdentifier;
        this.type = "public-key";
    }
}
