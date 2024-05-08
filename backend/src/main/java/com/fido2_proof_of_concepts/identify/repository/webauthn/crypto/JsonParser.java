package com.fido2_proof_of_concepts.identify.repository.webauthn.crypto;

import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.CollectedClientData;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 * Json parser Utils.
 */
public class JsonParser {

    /**
     * Converts json to byte array.
     *
     * @param json given json
     * @return array og bytes
     * @throws JSONException
     */
    public static byte[] JsonToByteArray(String json) throws JSONException {
        JSONArray array = new JSONObject(json).getJSONArray("data");
        byte[] bytes = new byte[array.length()];
        for (int i = 0; i < array.length(); i++) {
            bytes[i] = (byte) array.getInt(i);
        }
        return bytes;
    }

    /**
     * Deserializes client data json into {@link CollectedClientData}
     *
     * @param serializedClientData json Client data
     * @return {@link CollectedClientData}
     * @throws JSONException
     */
    public static CollectedClientData getClientData(String serializedClientData) throws JSONException {
        CollectedClientData clientData = new CollectedClientData();
        clientData.setChallenge(getChallenge(serializedClientData));
        clientData.setCrossOrigin(getCrossOrigin(serializedClientData));
        clientData.setOrigin(getOrigin(serializedClientData));
        clientData.setType(getType(serializedClientData));
        return clientData;
    }

    private static String getChallenge(String json) throws JSONException {
        String decodedChallenge = new JSONObject(json).getString("challenge");
        return BASE64.decode(decodedChallenge);
    }

    private static boolean getCrossOrigin(String json) throws JSONException {
        String crossOrigin = null;
        try {
            crossOrigin = new JSONObject(json).getString("crossOrigin");
        } catch (JSONException e) {
            crossOrigin = "false";
        }
        switch (crossOrigin) {
            case "false":
                return false;
            case "true":
                return true;
            default:
                return false;

        }
    }

    private static String getOrigin(String json) throws JSONException {
        return new JSONObject(json).getString("origin");
    }

    private static String getType(String json) throws JSONException {
        return new JSONObject(json).getString("type");
    }
}
