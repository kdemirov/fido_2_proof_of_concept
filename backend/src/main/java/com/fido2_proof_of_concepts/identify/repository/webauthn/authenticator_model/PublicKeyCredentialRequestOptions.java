package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
The PublicKeyCredentialRequestOptions dictionary
supplies get() with the data it needs to generate
an assertion object.
Its challenge member MUST be present,
while its other members are OPTIONAL.
more information here..
https://w3c.github.io/webauthn/#dictdef-publickeycredentialrequestoptions
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicKeyCredentialRequestOptions {
    private String challenge;
    private long timeout;
    private AuthenticationCeremonyId authenticationCeremonyId;
}
