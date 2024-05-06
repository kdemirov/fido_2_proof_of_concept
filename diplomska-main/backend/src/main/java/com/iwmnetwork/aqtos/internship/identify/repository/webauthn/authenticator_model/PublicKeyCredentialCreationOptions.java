/*
we use this object for sending parameters
for creating Attestation Response Object
more information here ...
https://w3c.github.io/webauthn/#dictdef-publickeycredentialcreationoptions
* */
package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicKeyCredentialCreationOptions {
    private RegistrationCeremonyId registrationCeremonyId;
    private PublicKeyCredentialRpEntity rp;
    private PublicKeyCredentialUserEntity user;
    private String challenge;
    private PublicKeyCredentialParameters pubKeyCredParams;
    private long timeout;
    private AuthenticatorSelectionCriteria authenticatorSelection;
    private String attestation;
}
