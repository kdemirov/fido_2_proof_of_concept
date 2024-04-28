package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.*;
import java.util.Arrays;


public class SHA256 {
    public static boolean verify(byte[] hashedBytes, byte[] toVerify) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] toCheck = digest.digest(toVerify);
        return Arrays.equals(hashedBytes, toCheck);
    }

    public static boolean verifySignature(AuthenticatorAttestationResponse response,
                                          byte[] clientData)
            throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        X509Certificate certificate = decodeCertificate(response.getX5c());
        PublicKey pk = certificate.getPublicKey();
        Signature ecdsaVerify = Signature.getInstance(certificate.getSigAlgName());
        ecdsaVerify.initVerify(pk);
        byte[] clientDataHash = hashClientData(clientData);
        ecdsaVerify.update(concatenateAuthDataAndClientData(response.getAuthData(), clientDataHash));
        return ecdsaVerify.verify(response.getSig());
    }

    public static boolean verifyAssertionSignature(AuthenticatorAssertionResponse response,
                                                   byte[] clientData,
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
        byte[] clientDataHash = hashClientData(clientData);
        signature.initVerify(key);
        signature.update(concatenateAuthDataAndClientData(response.getAuthenticatorData(), clientDataHash));
        return signature.verify(response.getSignature());
    }

    private static byte[] hashClientData(byte[] clientData) throws NoSuchAlgorithmException {
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
