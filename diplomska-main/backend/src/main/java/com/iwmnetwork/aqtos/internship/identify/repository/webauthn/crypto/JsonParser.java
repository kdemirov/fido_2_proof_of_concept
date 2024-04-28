package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto;
public class JsonParser {
    public static byte[] JsonToByteArray(String json) throws JSONException {
        JSONArray array = new JSONObject(json).getJSONArray("data");
        byte[] bytes = new byte[array.length()];
        for (int i = 0; i < array.length(); i++) {
            bytes[i] = (byte) array.getInt(i);
        }
        return bytes;
    }

    public static CollectedClientData getClientData(String json) throws JSONException {
        CollectedClientData clientData = new CollectedClientData();
        byte[] bytes = JsonParser.JsonToByteArray(json);
        String serializedClientData = Utf8Decoder.bytesToUtf8String(bytes);
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
        try{
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
