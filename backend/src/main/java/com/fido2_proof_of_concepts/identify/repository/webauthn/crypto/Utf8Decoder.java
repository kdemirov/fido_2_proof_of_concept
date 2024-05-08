package com.fido2_proof_of_concepts.identify.repository.webauthn.crypto;

import java.nio.charset.StandardCharsets;

/**
 * Utf 8 decoder utils.
 */
public class Utf8Decoder {

    /**
     * Converts bytes to string with standard charsets utf 8.
     *
     * @param bytes given bytes
     * @return string
     */
    public static String bytesToUtf8String(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
