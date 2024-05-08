package com.fido2_proof_of_concepts.identify.model.dto;

import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Response from frontend call credentials.create() needed for
 * initializing the registration ceremony commands.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PublicKeyCredentialCreationResponse {
    private RegistrationCeremonyId registrationCeremonyId;
    private String id;
    private String type;
    private String attestationObject;
    private String username;
    private String clientDataHash;
}
