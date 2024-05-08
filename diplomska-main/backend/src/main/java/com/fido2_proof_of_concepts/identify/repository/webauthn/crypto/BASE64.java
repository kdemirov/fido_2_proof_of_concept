package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto;

import java.util.Base64;

/**
 * Base 64 Utils.
 */
public class BASE64 {

    /**
     * Decodes given base 64 string.
     *
     * @param base64String given base64 string.
     * @return decoded string
     */
    public static String decode(String base64String) {
        byte[] bytes = Base64.getDecoder().decode(base64String);
        return new String(bytes);
    }
}
