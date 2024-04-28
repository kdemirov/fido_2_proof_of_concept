/*
we use this object to to specify
our requirements regarding authenticator attributes.
more information here ..
https://w3c.github.io/webauthn/#dictdef-authenticatorselectioncriteria
* */
package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatorSelectionCriteria {
    private String userVerification;
}
