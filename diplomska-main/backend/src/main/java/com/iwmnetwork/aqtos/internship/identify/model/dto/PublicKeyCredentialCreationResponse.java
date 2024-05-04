package com.iwmnetwork.aqtos.internship.identify.model.dto;

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
    private String registrationCeremonyId;
    private String id;
    private String type;
    private String attestationObject;
    private String username;
    private String clientDataHash;
}
