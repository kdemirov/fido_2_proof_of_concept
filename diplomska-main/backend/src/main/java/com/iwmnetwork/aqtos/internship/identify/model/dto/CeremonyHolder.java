package com.iwmnetwork.aqtos.internship.identify.model.dto;

import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ceremony holder for in memory repository.
 */
@NoArgsConstructor
@Setter
@Getter
public class CeremonyHolder {
    protected CollectedClientData clientData;
    protected byte[] clientDataHash;
    protected String jsonText;
}
