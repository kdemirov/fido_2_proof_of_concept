package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto;

import java.nio.charset.StandardCharsets;

public class Utf8Decoder {
    public static String bytesToUtf8String(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
