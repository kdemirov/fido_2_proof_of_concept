package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Public key
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublicKey {
    int keyType;
    int alg;
    int curveType;
    byte[] xCoordinate;
    byte[] yCoordinate;
}
