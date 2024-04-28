/*
The PublicKeyCredentialRequestOptions dictionary
supplies get() with the data it needs to generate
an assertion object.
Its challenge member MUST be present,
while its other members are OPTIONAL.
more information here..
https://w3c.github.io/webauthn/#dictdef-publickeycredentialrequestoptions
* */
package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicKeyCredentialRequestOptions {
    private String challenge;
    private long timeout;

}
