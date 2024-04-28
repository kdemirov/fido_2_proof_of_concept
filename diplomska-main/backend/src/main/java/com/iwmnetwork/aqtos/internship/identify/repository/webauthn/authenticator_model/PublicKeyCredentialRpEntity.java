/*
The PublicKeyCredentialRpEntity
dictionary is used to supply additional Relying Party
attributes when creating a new credential.
more information here...
https://w3c.github.io/webauthn/#dictdef-publickeycredentialrpentity
* */
package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicKeyCredentialRpEntity {
    private String id;
}
