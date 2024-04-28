/*
The AuthenticatorAssertionResponse interface represents
an authenticator's response to a client’s request
for generation of a new authentication assertion given
the WebAuthn Relying Party's challenge and
OPTIONAL list of credentials it is aware of.
This response contains a cryptographic signature
proving possession of the credential private key,
and optionally evidence of user consent to a specific transaction.
more information about this object you can find here...
https://w3c.github.io/webauthn/#authenticatorassetionresponse
* */
package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatorAssertionResponse extends AuthenticatorResponse {
    private byte[] authenticatorData;
    private byte[] signature;
    private byte[] userHandle;
}
