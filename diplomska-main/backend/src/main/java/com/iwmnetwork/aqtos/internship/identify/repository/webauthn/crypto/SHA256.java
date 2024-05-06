package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto;

import co.nstant.in.cbor.CborException;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAssertionResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.*;
import java.util.Arrays;

/**
 * SHA 256 Utils.
 */
public class SHA256 {

    /**
     * Verify hash.
     *
     * @param hashedBytes hashed bytes
     * @param toVerify    bytes to be hashed and verified
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static boolean verify(byte[] hashedBytes, byte[] toVerify) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] toCheck = digest.digest(toVerify);
        return Arrays.equals(hashedBytes, toCheck);
    }

    /**
     * Verify signature of {@link AuthenticatorAssertionResponse} over
     * the concatenation of auth data and client data hash.
     *
     * @param response       {@link AuthenticatorAssertionResponse}
     * @param clientDataHash client data hashed
     * @return true if the signature is valid otherwise false
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifySignature(AuthenticatorAttestationResponse response,
                                          byte[] clientDataHash)
            throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        X509Certificate certificate = decodeCertificate(response.getX5c());
        PublicKey pk = certificate.getPublicKey();
        Signature ecdsaVerify = Signature.getInstance(certificate.getSigAlgName());
        ecdsaVerify.initVerify(pk);
        ecdsaVerify.update(concatenateAuthDataAndClientData(response.getAuthData(), clientDataHash));
        return ecdsaVerify.verify(response.getSig());
    }

    /**
     * Verify {@link AuthenticatorAssertionResponse} signature over the
     * concatenation of auth data and client data hash.
     *
     * @param response       {@link AuthenticatorAssertionResponse}
     * @param clientDataHash client data hashed
     * @param publicKey      public key of saved fido user who is trying to authenticate
     * @return true if signature is valid otherwise false
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws CborException
     * @throws InvalidParameterSpecException
     */
    public static boolean verifyAssertionSignature(AuthenticatorAssertionResponse response,
                                                   byte[] clientDataHash,
                                                   byte[] publicKey) throws NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException, SignatureException,
            CborException, InvalidParameterSpecException {
        byte[] xCoordinate = CBORDecoder.decodePublicKey(publicKey).getXCoordinate();
        byte[] yCoordinate = CBORDecoder.decodePublicKey(publicKey).getYCoordinate();
        ECPoint point = new ECPoint(new BigInteger(1, xCoordinate), new BigInteger(1, yCoordinate));
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC");
        algorithmParameters.init(new ECGenParameterSpec("secp256r1"));
        ECParameterSpec parameterSpec = algorithmParameters.getParameterSpec(ECParameterSpec.class);
        ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(point, parameterSpec);
        Signature signature = Signature.getInstance("SHA256withECDSA");
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey key = keyFactory.generatePublic(publicKeySpec);
        signature.initVerify(key);
        signature.update(concatenateAuthDataAndClientData(response.getAuthenticatorData(), clientDataHash));
        return signature.verify(response.getSignature());
    }

    /**
     * Hashes client data.
     *
     * @param clientData given client data bytes
     * @return hashed client data bytes.
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hashClientData(byte[] clientData) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(clientData);
    }

    private static byte[] concatenateAuthDataAndClientData(byte[] authData, byte[] clientDataHashed) {
        int size = authData.length + clientDataHashed.length;
        byte[] concatenatedData = new byte[size];
        System.arraycopy(authData, 0, concatenatedData, 0, authData.length);
        System.arraycopy(clientDataHashed, 0, concatenatedData, authData.length, clientDataHashed.length);
        return concatenatedData;
    }

    private static X509Certificate decodeCertificate(byte[] x5c) throws CertificateException {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        InputStream inputStream = new ByteArrayInputStream(x5c);
        return (X509Certificate) factory.generateCertificate(inputStream);
    }
}
