/*
The AuthenticatorAttestationResponse interface
represents the authenticator's response
to a client’s request for the creation of a new public key
credential. It contains information about the new credential
that can be used to identify it for later use, and metadata
that can be used by the WebAuthn Relying Party to assess the characteristics of the credential
during registration
more information you can find here ...
https://w3c.github.io/webauthn/#authenticatorattestationresponse
* */
package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AuthenticatorAttestationResponse extends AuthenticatorResponse {
    @Column(name="att_response_alg", insertable = false)
    private int alg;
    @Column(name="att_response_sig", insertable = false)
    private byte[] sig;
    @Column(name="att_response_x5c", insertable = false)
    private byte[] x5c;
    @Column(name = "att_response_auth_data", insertable = false)
    private byte[] authData;
    @Column(name = "att_response_fmt", insertable = false)
    private String fmt;


}
