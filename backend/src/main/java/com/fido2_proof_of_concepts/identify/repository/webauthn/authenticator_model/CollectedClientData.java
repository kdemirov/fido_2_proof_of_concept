/*
we use this object for verifying the Client Data
more information here ...
https://w3c.github.io/webauthn/#dictdef-authenticatorselectioncriteria
* */

package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectedClientData {
    private String type;
    private String challenge;
    private String origin;
    boolean crossOrigin;
}
