package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuthenticatorResponse {
    private byte[] clientDataJSON;
}
