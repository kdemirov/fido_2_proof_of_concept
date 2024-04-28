package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.exceptions;

public class VerificationFailedException extends RuntimeException {
    public VerificationFailedException() {
        super("Verification failed");
    }
}
