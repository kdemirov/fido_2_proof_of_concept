package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto;

import com.iwmnetwork.aqtos.internship.identify.model.exceptions.Fido2Exception;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Relying party utils.
 */
public class RelyingPartyUtils {


    /**
     * Verify rpId hash in auth data with our rpId hash.
     *
     * @param authData  auth data bytes
     * @param rpIdBytes our rp id bytes
     * @return true if the hash in auth data matches with our rp id hash
     */
    public static boolean verifyRrIdHash(byte[] authData, byte rpIdBytes[]) {

        byte[] rpIdHash = Arrays.copyOfRange(authData, 0, 32);
        boolean verify;
        try {
            verify = SHA256.verify(rpIdHash, rpIdBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new Fido2Exception(e);
        }
        return verify;
    }

    /**
     * Getting signature count from roaming authenticator
     * |rpIdHash|flags|signCount|attestedCredentialData|extensions
     * |32 bytes|1 byte|4 bytes|variable optional|variable optional
     * more information on https://www.w3.org/TR/webauthn/#authenticator-data
     *
     * @param authData raw received authenticator  data
     * @return 32 bit unsigned big-endian integer
     */
    public static int getSignCount(byte[] authData) {
        byte[] signCount = Arrays.copyOfRange(authData, 33, 37);
        ByteBuffer buffer = ByteBuffer.wrap(signCount);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getInt();
    }

    /**
     * Getting flags from authenticator data
     * |rpIdHash|flags|signCount|attestedCredentialData|extensions
     * |32 bytes|1 byte|4 bytes|variable optional|variable optional
     *
     * @param authData received authenticator data
     * @return the bit in position 32 which is flags
     */
    private static byte getFlags(byte[] authData) {
        return authData[32];
    }

    /**
     * Verify if user is present in received authData from flags
     * step 16 from https://www.w3.org/TR/webauthn/#sctn-verifying-assertion
     * more on https://www.w3.org/TR/webauthn/#authenticator-data
     *
     * @param authData received authenticator data
     * @return true if the first bit is set to 1 in flags
     */
    public static boolean verifyUserPresent(byte[] authData) {
        byte userPresent = getFlags(authData);
        return (byte) (userPresent & 1) == 1;
    }

    /**
     * Verify if user is verified in authenticator data in flags bits
     * step 17 from https://www.w3.org/TR/webauthn/#sctn-verifying-assertion
     *
     * @param authData received authenticator data
     * @return true if the second bit in flags is set
     */
    public static boolean verifyUserVerified(byte[] authData) {
        byte userVerifies = getFlags(authData);
        return (byte) ((userVerifies >> 2) & 1) == 1;
    }
}
