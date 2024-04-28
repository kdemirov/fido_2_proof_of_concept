package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.crypto;

import java.util.Base64;

public class BASE64 {
    public static String decode(String base64String) {
        byte[] bytes = Base64.getDecoder().decode(base64String);
        return new String(bytes);
    }
}
