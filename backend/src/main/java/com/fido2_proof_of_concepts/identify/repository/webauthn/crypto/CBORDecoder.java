package com.fido2_proof_of_concepts.identify.repository.webauthn.crypto;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.*;
import com.fido2_proof_of_concepts.identify.model.exceptions.Fido2Exception;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AuthenticatorAssertionResponse;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKey;
import com.fido2_proof_of_concepts.identify.repository.webauthn.enumerations.PublicKeyDescriptor;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.List;

/**
 * CBOR Utils.
 */
public class CBORDecoder {

    /**
     * Decodes {@link AuthenticatorAttestationResponse}
     *
     * @param json json
     * @return {@link AuthenticatorAttestationResponse}
     */
    public static AuthenticatorAttestationResponse decodeAttStmt(String json) {
        byte[] bytes;
        List<DataItem> attestationResponseItems;
        try {
            bytes = JsonParser.JsonToByteArray(json);
            attestationResponseItems = CborDecoder.decode(bytes);
        } catch (JSONException | CborException e) {
            throw new Fido2Exception(e);
        }
        return decodeAttestationResponse(attestationResponseItems);
    }

    private static AuthenticatorAttestationResponse decodeAttestationResponse(List<DataItem> attestationResponseItems) {
        AuthenticatorAttestationResponse response = new AuthenticatorAttestationResponse();
        Map map = (Map) attestationResponseItems.get(0);
        for (DataItem item : map.getKeys()) {
            UnicodeString mapKey = (UnicodeString) item;
            if (mapKey.getString().equals("fmt")) {
                UnicodeString fmt = (UnicodeString) map.get(item);
                response.setFmt(fmt.getString());
            }
            if (mapKey.getString().equals("attStmt")) {
                response.setAlg(getAttStmtAlg(map.get(item)));
                response.setSig(getAttStmtSig(map.get(item)));
                response.setX5c(getAttStmtX5c(map.get(item)));
            }
            if (mapKey.getString().equals("authData")) {
                response.setAuthData(getAttAuthData(map.get(item)));
            }
        }
        return response;
    }

    /**
     * Decodes {@link AuthenticatorAssertionResponse}
     *
     * @param authData   auth data
     * @param userHandle user handle
     * @param signature  signature
     * @return {@link AuthenticatorAssertionResponse}
     */
    public static AuthenticatorAssertionResponse decodeAssObj(String authData,
                                                              String userHandle,
                                                              String signature
    ) {
        AuthenticatorAssertionResponse response = new AuthenticatorAssertionResponse();
        try {
            response.setAuthenticatorData(JsonParser.JsonToByteArray(authData));
            response.setUserHandle(JsonParser.JsonToByteArray(userHandle));
            response.setSignature(JsonParser.JsonToByteArray(signature));
        } catch (JSONException e) {
            throw new Fido2Exception(e);
        }
        return response;
    }

    private static int getAttStmtAlg(DataItem item) {
        Map attStmtMap = (Map) item;
        for (DataItem attStmtItem : attStmtMap.getKeys()) {
            UnicodeString mapKey = (UnicodeString) attStmtItem;
            if (mapKey.getString().equals("alg")) {
                NegativeInteger alg = (NegativeInteger) attStmtMap.get(attStmtItem);
                return alg.getValue().intValue();
            }
        }
        return 0;
    }

    private static byte[] getAttStmtSig(DataItem item) {
        Map attStmtMap = (Map) item;
        for (DataItem attStmtItem : attStmtMap.getKeys()) {
            UnicodeString mapKey = (UnicodeString) attStmtItem;
            if (mapKey.getString().equals("sig")) {
                ByteString sig = (ByteString) attStmtMap.get(attStmtItem);
                return sig.getBytes();
            }
        }
        return null;
    }

    private static byte[] getAttStmtX5c(DataItem item) {
        Map attStmtMap = (Map) item;
        for (DataItem attStmtItem : attStmtMap.getKeys()) {
            UnicodeString mapKey = (UnicodeString) attStmtItem;
            if (mapKey.getString().equals("x5c")) {
                Array x5cArray = (Array) attStmtMap.get(attStmtItem);
                ByteString x5c = (ByteString) x5cArray.getDataItems().get(0);
                return x5c.getBytes();
            }
        }
        return null;
    }

    private static byte[] getAttAuthData(DataItem item) {
        ByteString byteString = (ByteString) item;
        return byteString.getBytes();
    }

    /**
     * Decodes public key.
     *
     * @param publicKey public key bytes.
     * @return {@link  PublicKey}
     */
    public static PublicKey decodePublicKey(byte[] publicKey) {
        List<DataItem> dataItems;
        try {
            dataItems = CborDecoder.decode(publicKey);
        } catch (CborException e) {
            throw new Fido2Exception(e);
        }
        return decodePk(dataItems);
    }

    private static PublicKey decodePk(List<DataItem> dataItems) {
        PublicKey pk = new PublicKey();
        Map map = (Map) dataItems.get(0);
        for (DataItem dataItem : map.getKeys()) {
            if (dataItem instanceof UnsignedInteger) {
                if (pk.getKeyType() == 0) {
                    pk.setKeyType(decodePkKeyType(map, dataItem));
                }
                if (pk.getAlg() == 0) {
                    pk.setAlg(decodeAlg(map, dataItem));
                }
            }
            if (dataItem instanceof NegativeInteger) {
                if (pk.getCurveType() == 0) {
                    pk.setCurveType(decodeCurveType(map, dataItem));
                }
                if (pk.getXCoordinate() == null) {
                    pk.setXCoordinate(decodeXCoordinate(map, dataItem));
                }
                if (pk.getYCoordinate() == null) {
                    pk.setYCoordinate(decodeYCoordinate(map, dataItem));
                }
            }

        }
        return pk;
    }

    private static int decodePkKeyType(Map map, DataItem dataItem) {
        UnsignedInteger keyType = (UnsignedInteger) dataItem;
        if (keyType.getValue().intValue() == PublicKeyDescriptor.KEY_TYPE.descriptor) {
            UnsignedInteger keyTypeValue = (UnsignedInteger) map.get(keyType);
            return keyTypeValue.getValue().intValue();
        }
        return 0;
    }

    private static byte[] decodeXCoordinate(Map map, DataItem dataItem) {
        NegativeInteger xCoordinateKey = (NegativeInteger) dataItem;
        if (xCoordinateKey.getValue().intValue() == PublicKeyDescriptor.X_COORDINATE.descriptor) {
            ByteString xCoordinate = (ByteString) map.get(dataItem);
            return xCoordinate.getBytes();
        }
        return null;
    }

    private static byte[] decodeYCoordinate(Map map, DataItem dataItem) {
        NegativeInteger yCoordinateKey = (NegativeInteger) dataItem;
        if (yCoordinateKey.getValue().intValue() == PublicKeyDescriptor.Y_COORDINATE.descriptor) {
            ByteString yCoordinate = (ByteString) map.get(dataItem);
            return yCoordinate.getBytes();
        }
        return null;
    }

    private static int decodeCurveType(Map map, DataItem dataItem) {
        NegativeInteger curveKeyType = (NegativeInteger) dataItem;
        if (curveKeyType.getValue().intValue() == PublicKeyDescriptor.CURVE_TYPE.descriptor) {
            UnsignedInteger curveTypeValue = (UnsignedInteger) map.get(dataItem);
            return curveTypeValue.getValue().intValue();
        }
        return 0;
    }

    private static int decodeAlg(Map map, DataItem dataItem) {
        UnsignedInteger algKey = (UnsignedInteger) dataItem;
        if (algKey.getValue().intValue() == PublicKeyDescriptor.ALG.descriptor) {
            NegativeInteger alg = (NegativeInteger) map.get(dataItem);
            return alg.getValue().intValue();
        }
        return 0;
    }
}
