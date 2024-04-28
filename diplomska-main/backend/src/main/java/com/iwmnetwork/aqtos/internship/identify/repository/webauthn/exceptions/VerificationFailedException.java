package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions;

public class VerificationFailedException extends RuntimeException {
    public VerificationFailedException() {
        super("Verification failed");
    }
}
