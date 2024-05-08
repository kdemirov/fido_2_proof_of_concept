/*
The PublicKeyCredentialUserEntity dictionary
is used to supply additional user account attributes
when creating a new credential.
more information here...
https://w3c.github.io/webauthn/#dictdef-publickeycredentialuserentity
* */
package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicKeyCredentialUserEntity {
    private String id;
    private String displayName;
}
