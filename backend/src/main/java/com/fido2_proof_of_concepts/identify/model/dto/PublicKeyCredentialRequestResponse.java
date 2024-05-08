package com.fido2_proof_of_concepts.identify.model.dto;

import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Response from credential.get() call from frontend needed for
 * initializations commands for authentication ceremony.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PublicKeyCredentialRequestResponse {
    private AuthenticationCeremonyId authenticationCeremonyId;
    private String id;
    private String signature;
    private String authData;
    private String userHandle;
    private String clientDataJSON;
    private String type;
}
