package com.fido2_proof_of_concepts.identify.repository;

import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.model.dto.AuthenticationCeremonyInMemory;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AuthenticatorAssertionResponse;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.CollectedClientData;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.fido2_proof_of_concepts.identify.repository.webauthn.crypto.JsonParser.JsonToByteArray;

/**
 * Authentication in memory repository
 */
@Component
public class AuthenticationCeremonyInMemoryRepository {

    public static final Map<AuthenticationCeremonyId, AuthenticationCeremonyInMemory> MEMORY_MAP = new HashMap<>();

    /**
     * Saves {@link PublicKeyCredentialRequestOptions} with given authentication id.
     *
     * @param id      given authentication id
     * @param options options
     */
    public void saveOptions(AuthenticationCeremonyId id, PublicKeyCredentialRequestOptions options) {
        saveEntry(id, (key) -> {
            AuthenticationCeremonyInMemory memory = new AuthenticationCeremonyInMemory();
            memory.setOptions(options);
            return memory;
        }, (key, val) -> {
            val.setOptions(options);
            return val;
        });
    }

    /**
     * Saves {@link AuthenticatorAssertionResponse} by given authentication ceremony id.
     *
     * @param id       given authentication ceremony id
     * @param response given {@link AuthenticatorAssertionResponse}
     */
    public void saveResponse(AuthenticationCeremonyId id, AuthenticatorAssertionResponse response) {
        saveEntry(id,
                key -> {
                    AuthenticationCeremonyInMemory memory = new AuthenticationCeremonyInMemory();
                    memory.setResponse(response);
                    return memory;
                }, (key, val) -> {
                    val.setResponse(response);
                    return val;
                });
    }

    /**
     * Saves credential id by given authentication ceremony id.
     *
     * @param id           authentication ceremony id
     * @param credentialId credential id
     */
    public void saveCredentialId(AuthenticationCeremonyId id, String credentialId) {
        saveEntry(id,
                key -> {
                    AuthenticationCeremonyInMemory memory = new AuthenticationCeremonyInMemory();
                    try {
                        memory.setCredentialId(JsonToByteArray(credentialId));
                    } catch (JSONException o_O) {
                        throw new RuntimeException(o_O);
                    }
                    return memory;
                }, (key, val) -> {
                    try {
                        val.setCredentialId(JsonToByteArray(credentialId));
                    } catch (JSONException o_O) {
                        throw new RuntimeException(o_O);
                    }
                    return val;
                });
    }

    /**
     * Saves fido user by given authentication ceremony id.
     *
     * @param ceremonyId authentication ceremony id
     * @param fidoUser   fido user
     */
    public void saveFidoUser(AuthenticationCeremonyId ceremonyId, FidoUser fidoUser) {
        saveEntry(ceremonyId, (key) -> {
            AuthenticationCeremonyInMemory memory = new AuthenticationCeremonyInMemory();
            memory.setFidoUser(fidoUser);
            return memory;
        }, (key, val) -> {
            val.setFidoUser(fidoUser);
            return val;
        });
    }

    /**
     * Saves client data json by given authentication ceremony id.
     *
     * @param id       authentication ceremony id
     * @param jsonText client data json text
     */
    public void saveClientDataJsonText(AuthenticationCeremonyId id, String jsonText) {
        saveEntry(id, key -> {
            AuthenticationCeremonyInMemory memory = new AuthenticationCeremonyInMemory();
            memory.setJsonText(jsonText);
            return memory;
        }, (key, val) -> {
            val.setJsonText(jsonText);
            return val;
        });
    }

    /**
     * Saves {@link CollectedClientData} by given authentication ceremony id.
     *
     * @param authenticationCeremonyId authentication ceremony id
     * @param clientData               {@link CollectedClientData}
     */
    public void saveClientData(AuthenticationCeremonyId authenticationCeremonyId, CollectedClientData clientData) {
        saveEntry(authenticationCeremonyId, key -> {
            AuthenticationCeremonyInMemory memory = new AuthenticationCeremonyInMemory();
            memory.setClientData(clientData);
            return memory;
        }, (key, val) -> {
            val.setClientData(clientData);
            return val;
        });
    }

    /**
     * Saves hashed client data by given authentication ceremony id.
     *
     * @param id               authentication ceremony id
     * @param hashedClientData hashed client data.
     */
    public void saveClientDataHash(AuthenticationCeremonyId id, byte[] hashedClientData) {
        saveEntry(id, (key) -> {
            AuthenticationCeremonyInMemory memory = new AuthenticationCeremonyInMemory();
            memory.setClientDataHash(hashedClientData);
            return memory;
        }, (key, val) -> {
            val.setClientDataHash(hashedClientData);
            return val;
        });
    }

    /**
     * Finds hashed client data by given authentication ceremony id.
     *
     * @param id authentication ceremony id
     */
    public byte[] getClientDataHash(AuthenticationCeremonyId id) {
        return MEMORY_MAP.get(id).getClientDataHash();
    }

    /**
     * Finds public key by given authentication ceremony id.
     *
     * @param authenticationCeremonyId given authentication ceremony id
     * @return public key
     */
    public byte[] getPublicKey(AuthenticationCeremonyId authenticationCeremonyId) {
        return MEMORY_MAP.get(authenticationCeremonyId).getFidoUser().getPublicKey();
    }

    /**
     * Finds fido user by given authentication ceremony id.
     *
     * @param id authentication ceremony id
     * @return {@link FidoUser}
     */
    public FidoUser getFidoUser(AuthenticationCeremonyId id) {
        return MEMORY_MAP.get(id).getFidoUser();
    }

    /**
     * Finds {@link CollectedClientData} by given authentication ceremony id
     *
     * @param id authentication ceremony id
     * @return {@link CollectedClientData}
     */
    public CollectedClientData getClientData(AuthenticationCeremonyId id) {
        return MEMORY_MAP.get(id).getClientData();
    }

    /**
     * Finds client data json text by given authentication ceremony id.
     *
     * @param id authentication ceremony id
     * @return client data json text
     */
    public String getClientDataJsonText(AuthenticationCeremonyId id) {
        return MEMORY_MAP.get(id).getJsonText();
    }

    /**
     * Finds credential id by given authentication ceremony id.
     *
     * @param id authentication ceremony id
     * @return credential id.
     */
    public byte[] getCredentialId(AuthenticationCeremonyId id) {
        return MEMORY_MAP.get(id).getCredentialId();
    }

    /**
     * Finds {@link PublicKeyCredentialRequestOptions} by given authentication ceremony id.
     *
     * @param authenticationCeremonyId authentication ceremony id
     * @return {@link PublicKeyCredentialRequestOptions}
     */
    public PublicKeyCredentialRequestOptions getOptions(AuthenticationCeremonyId authenticationCeremonyId) {
        return MEMORY_MAP.get(authenticationCeremonyId).getOptions();
    }

    /**
     * Finds {@link AuthenticatorAssertionResponse} by given authentication ceremony id.
     *
     * @param authenticationCeremonyId given authentication ceremony id
     * @return {@link AuthenticatorAssertionResponse}
     */
    public AuthenticatorAssertionResponse getResponse(AuthenticationCeremonyId authenticationCeremonyId) {
        return MEMORY_MAP.get(authenticationCeremonyId).getResponse();
    }

    private void saveEntry(AuthenticationCeremonyId id,
                          Function<AuthenticationCeremonyId, AuthenticationCeremonyInMemory> keyNoPresentFunction,
                          BiFunction<AuthenticationCeremonyId,
                                  AuthenticationCeremonyInMemory,
                                  AuthenticationCeremonyInMemory> keyPresentFunction) {
        if (MEMORY_MAP.containsKey(id)) {
            MEMORY_MAP.computeIfPresent(id, keyPresentFunction);
        } else {
            MEMORY_MAP.computeIfAbsent(id, keyNoPresentFunction);
        }
    }
}
