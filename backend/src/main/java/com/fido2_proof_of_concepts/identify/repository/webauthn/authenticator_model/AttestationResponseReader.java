package com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model;

import java.util.Arrays;

/**
 * Attestation Response Reader Utils.
 */
public class AttestationResponseReader {

    /**
     * Decodes {@link AttestedCredentialData}.
     *
     * @param authData auth data
     * @return {@link AttestedCredentialData}
     */
    public static AttestedCredentialData decodeAttestedCredentialData(byte[] authData) {
        AttestedCredentialData credentialData = new AttestedCredentialData();
        //2 bytes represent an unsigned 16 bit integer
        byte[] idLenBytes = Arrays.copyOfRange(authData, 53, 55);
        credentialData.setCredentialIdLength(getCredentialIdLength(idLenBytes));
        credentialData.setCredentialId(getCredentialId(authData, credentialData.getCredentialIdLength()));
        credentialData.setCredentialPublicKey(getPublicKeyBytes(authData, credentialData.getCredentialIdLength()));
        return credentialData;
    }

    private static short getCredentialIdLength(byte[] idLenBytes) {
        return (short) (((idLenBytes[0] & 0xFF) << 8) | (idLenBytes[1] & 0xFF));
    }

    private static byte[] getCredentialId(byte[] authData, short credentialIdLength) {
        return Arrays.copyOfRange(authData, 55, 55 + credentialIdLength);
    }

    private static byte[] getPublicKeyBytes(byte[] authData, short credentialIdLength) {
        return Arrays.copyOfRange(authData, 55 + credentialIdLength, authData.length);
    }
}
