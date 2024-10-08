/*
we use this object to to specify
our requirements regarding authenticator attributes.
more information here ..
https://w3c.github.io/webauthn/#dictdef-authenticatorselectioncriteria
* */
package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatorSelectionCriteria {
    private String userVerification;
}
