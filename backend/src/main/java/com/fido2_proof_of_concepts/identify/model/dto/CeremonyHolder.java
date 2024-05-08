package com.fido2_proof_of_concepts.identify.model.dto;

import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.CollectedClientData;
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
