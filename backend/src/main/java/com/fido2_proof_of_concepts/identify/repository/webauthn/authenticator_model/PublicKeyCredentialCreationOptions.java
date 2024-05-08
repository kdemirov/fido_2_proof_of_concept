/*
we use this object for sending parameters
for creating Attestation Response Object
more information here ...
https://w3c.github.io/webauthn/#dictdef-publickeycredentialcreationoptions
* */
package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
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
