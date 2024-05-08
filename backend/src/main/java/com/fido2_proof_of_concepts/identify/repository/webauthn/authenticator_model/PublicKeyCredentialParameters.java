/*
this object is also part of PublicKeyCredentialCreationOptions
for sending type of alg
https://w3c.github.io/webauthn/#dictdef-publickeycredentialparameters
* */
package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import lombok.Data;

@Data
public class PublicKeyCredentialParameters {
    private int alg;
    private String type;

    /**
     * Constructor.
     */
    public PublicKeyCredentialParameters() {
        this.alg = Constants.COSEAlgorithmIdentifier;
        this.type = "public-key";
    }
}
