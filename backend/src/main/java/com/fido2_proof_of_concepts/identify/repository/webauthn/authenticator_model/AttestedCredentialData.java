/*
AttestedCredentialData object is part of AuthenticatorData
more information here...
https://w3c.github.io/webauthn/#attestedcredentialdata
* */
package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttestedCredentialData {
    private byte[] aaguid;
    private short credentialIdLength;
    private byte[] credentialId;
    private byte[] credentialPublicKey;
}
