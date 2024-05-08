package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions;

/**
 * Verification failed exception for all steps defined in authentication and registration ceremony.
 */
public class VerificationFailedException extends RuntimeException {

    /**
     * Constructor.
     */
    public VerificationFailedException() {
        super("Verification failed");
    }
}
