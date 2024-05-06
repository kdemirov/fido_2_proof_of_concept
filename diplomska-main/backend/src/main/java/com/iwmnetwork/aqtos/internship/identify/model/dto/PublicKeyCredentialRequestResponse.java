package com.iwmnetwork.aqtos.internship.identify.model.dto;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Response from credential.get() call from frontend.
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
