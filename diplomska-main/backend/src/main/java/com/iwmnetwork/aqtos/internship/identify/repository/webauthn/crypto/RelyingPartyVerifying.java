package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto;

import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.exceptions.Fido2Exception;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.*;
import org.springframework.core.env.Environment;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

public class RelyingPartyVerifying {

    private static Environment environment;

    /**
     * Verify AuthenticatorAttestationResponse received from authenticator
     * steps 3 - 5 from https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential
     *
     * @param response       decoded AuthenticatorAttestationResponse received from fido roaming authenticator
     * @param data           decoded received client data from fido roaming authenticator
     * @param credentialData decoded credential data received from fido roaming authenticator
     * @param clientData     raw client data received from fido roaming authenticator
     * @return true if all the steps all fulfilled
     * @throws Fido2Exception
     */
    public static boolean verify(AuthenticatorAttestationResponse response,
                                 CollectedClientData data,
                                 AttestedCredentialData credentialData,
                                 byte[] clientData) throws Fido2Exception {
        return (verifyCTypeCreate(data.getType()) &&
                verifyOrigin(data.getOrigin()) &&
                verifyRrIdHash(response.getAuthData()) &&
                verifyAlg(response.getAlg(), credentialData) &&
                verifyAttStmtSignature(response, clientData) &&
                verifyUserPresent(response.getAuthData()) &&
                verifyUserVerified(response.getAuthData()));

    }

    public static void setEnvironment(Environment environment) {
        RelyingPartyVerifying.environment = environment;
    }

    /**
     * Verify that collected client data type is create when we are registering our fido credentials
     * step 7 from https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential
     *
     * @param type received type from fido roaming authenticator
     * @return true if type equals webauthn.create
     */
    private static boolean verifyCTypeCreate(String type) {
        String typeCreate = environment.getProperty("fido.type_create", String.class, "webauthn.create");
        return type.equals(typeCreate);
    }

    /**
     * Verify that received relying party hashed id is the same as our relying party id
     * step 13 from https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential
     *
     * @param authData array of bytes received from fido roaming authenticator
     * @return true if the SHA-256 has of our relying party id is the as the received one
     */
    private static boolean verifyRrIdHash(byte[] authData) {

        byte[] rpIdHash = Arrays.copyOfRange(authData, 0, 32);
        boolean verify;
        byte[] rpIdBytes = environment
                .getProperty("fido.rpId", String.class, "localhost")
                .getBytes();
        try {
            verify = SHA256.verify(rpIdHash, rpIdBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new Fido2Exception(e);
        }
        return verify;
    }

    /**
     * Verifying that the alg parameter received from credential public key in authData matches our supported alg
     * step 16 from https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential
     *
     * @param alg            received alg COSEAlgorithmIdentifier from fido 2 authenticator
     * @param credentialData decoded AttestedCredentialData from fido 2 authenticator
     * @return true if we support the alg
     * @throws Fido2Exception
     */
    private static boolean verifyAlg(int alg, AttestedCredentialData credentialData)
            throws Fido2Exception {
        PublicKey pk = CBORDecoder
                .decodePublicKey(credentialData.getCredentialPublicKey());
        return alg == Constants.COSEAlgorithmIdentifier &&
                pk.getAlg() == Constants.COSEAlgorithmIdentifier;

    }

    /**
     * Verifying that the origin received from fido2 roaming authenticator matches our origin
     * This verifying is for registration and authentication
     * step 9 from https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential
     * step 13 from https://www.w3.org/TR/webauthn/#sctn-verifying-assertion
     *
     * @param origin received origin
     * @return true if matches
     */
    private static boolean verifyOrigin(String origin) {
        String appOrigin = environment.getProperty("fido.origin", String.class, "http://localhost:3000");
        return origin.equals(appOrigin);
    }

    /**
     * Verify that received attestation response conveys valid signature
     * step 19 from https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential
     *
     * @param response   decoded received response from fido 2 roaming authenticator
     * @param clientData raw received client data from fido 2 roaming authenticator
     * @return true if received sig from attStmt object is valid
     */
    private static boolean verifyAttStmtSignature(AuthenticatorAttestationResponse response,
                                                  byte[] clientData) {
        boolean verify;
        try {
            verify = SHA256.verifySignature(response, clientData);
        } catch (CertificateException |
                NoSuchAlgorithmException |
                InvalidKeyException |
                SignatureException e) {
            throw new Fido2Exception(e);
        }
        return verify;
    }

    /**
     * Verify that the type of request from collected client data is webauthn.get
     * this is only for authentication
     * step 11 from https://www.w3.org/TR/webauthn/#sctn-verifying-assertion
     *
     * @param type received request type
     * @return true if received type equals webauthn.get
     */
    private static boolean verifyCTypeGet(String type) {
        String typeGet = environment.getProperty("fido.type_get", String.class, "webauthn.get");
        return type.equals(typeGet);
    }

    /**
     * Verify that received assertion response sig is valid sig over the binary concatenation of authData and hashed client data
     * step 20 from https://www.w3.org/TR/webauthn/#sctn-verifying-assertion
     *
     * @param response   decoded AssertionResponse received from fido2 roaming authenticator
     * @param clientData raw client data received from fido2 roaming authenticator
     * @param publicKey  stored public key for the current fidoUser
     * @return true if the signature is valid
     */
    private static boolean verifyAssertionObjectSignature(AuthenticatorAssertionResponse response,
                                                          byte[] clientData,
                                                          byte[] publicKey) {
        boolean verify;
        try {
            verify = SHA256.verifyAssertionSignature(response, clientData, publicKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException |
                SignatureException | CborException | InvalidParameterSpecException e) {
            throw new Fido2Exception(e);
        }
        return verify;
    }

    /**
     * Important  steps for allowing fidoUser to be authenticated
     * more on  https://www.w3.org/TR/webauthn/#sctn-verifying-assertion
     *
     * @param response       decoded received response
     * @param clientData     decoded received client data
     * @param clientDataJSON raw received client data
     * @param publicKey      stored public key for current fidoUser
     * @return true if all steps all fulfilled
     * @throws Fido2Exception
     */
    public static boolean verify(AuthenticatorAssertionResponse response,
                                 CollectedClientData clientData,
                                 byte[] clientDataJSON,
                                 byte[] publicKey) throws Fido2Exception {
        return (verifyCTypeGet(clientData.getType()) &&
                verifyOrigin(clientData.getOrigin()) &&
                verifyRrIdHash(response.getAuthenticatorData()) &&
                verifyUserPresent(response.getAuthenticatorData()) &&
                verifyAssertionObjectSignature(response, clientDataJSON, publicKey));
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
    private int getSignCount(byte[] authData) {
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
    private static boolean verifyUserPresent(byte[] authData) {
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
    private static boolean verifyUserVerified(byte[] authData) {
        byte userVerifies = getFlags(authData);
        return (byte) ((userVerifies >> 2) & 1) == 1;
    }


}
