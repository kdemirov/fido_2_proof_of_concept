/*
The authenticator data structure encodes
contextual bindings made by the authenticator.
These bindings are controlled by the authenticator itself,
and derive their trust from the WebAuthn Relying Party's
assessment of the security properties of the authenticator.
more information about this object you can find here...
https://w3c.github.io/webauthn/#sctn-authenticator-data
* */

package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatorData {
    private byte[] rpIdHash;
    private byte userPresent;
    private byte rfu1;
    private byte userVerified;
    private byte attestedCredentialDataIncluded;
    private byte extensionIncluded;
    private int signatureCount;
    private byte[] attestedCredentialData;
    private byte[] extensions;
}
