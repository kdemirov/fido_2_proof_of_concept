package com.iwmnetwork.aqtos.internship.identify.repository;

import com.iwmnetwork.aqtos.internship.identify.model.aggregate.RegistrationCeremony;
import com.iwmnetwork.aqtos.internship.identify.model.dto.AuthenticationCeremonyInMemory;
import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.iwmnetwork.aqtos.internship.identify.model.dto.RegistrationCeremonyInMemory;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Registration Ceremony In Memory Repository
 */
@Component
@RequiredArgsConstructor
public class RegistrationCeremonyInMemoryRepository {

    public static final Map<RegistrationCeremonyId, RegistrationCeremonyInMemory> MEMORY = new HashMap<>();

    /**
     * Saves created {@link PublicKeyCredentialCreationOptions} for the registration ceremony with the
     * given id.
     *
     * @param id      given registration ceremony id
     * @param options created {@link PublicKeyCredentialCreationOptions}
     */
    public void setOptions(RegistrationCeremonyId id, PublicKeyCredentialCreationOptions options) {
        saveEntry(id,
                key -> {
                    RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
                    memory.setOptions(options);
                    return memory;
                }, (key, val) -> {
                    val.setOptions(options);
                    return val;
                });
    }

    /**
     * Saves received clientJsonText for the registration ceremony with the
     * given id.
     *
     * @param id       given registration ceremony id
     * @param jsonText received clientDataJsonText
     */
    public void setJsonText(RegistrationCeremonyId id, String jsonText) {
        saveEntry(id, key -> {
            RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
            memory.setJsonText(jsonText);
            return memory;
        }, (key, val) -> {
            val.setJsonText(jsonText);
            return val;
        });
    }

    /**
     * Saves hashed client data json text for the registration ceremony with the
     * given id.
     *
     * @param id             given registration ceremony id
     * @param clientDataHash hashed client data
     */
    public void setClientDataHash(RegistrationCeremonyId id, byte[] clientDataHash) {
        saveEntry(id, key -> {
            RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
            memory.setClientDataHash(clientDataHash);
            return memory;
        }, (key, val) -> {
            val.setClientDataHash(clientDataHash);
            return val;
        });
    }

    /**
     * Saves deserialized {@link CollectedClientData} for the registration ceremony with the
     * given id.
     *
     * @param registrationCeremonyId given registration ceremony id
     * @param clientData             deserialized {@link CollectedClientData}
     */
    public void setClientCollectedData(RegistrationCeremonyId registrationCeremonyId, CollectedClientData clientData) {
        saveEntry(registrationCeremonyId, key -> {
            RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
            memory.setClientData(clientData);
            return memory;
        }, (key, val) -> {
            val.setClientData(clientData);
            return val;
        });
    }

    /**
     * Saves decoded {@link AttestedCredentialData} for the registration ceremony with the
     * given id.
     *
     * @param registrationCeremonyId given registration ceremony id
     * @param credentialData         decoded {@link AttestedCredentialData}
     */
    public void setAttestedCredentialData(RegistrationCeremonyId registrationCeremonyId, AttestedCredentialData credentialData) {
        saveEntry(registrationCeremonyId, key -> {
            RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
            memory.setAttestedCredentialData(credentialData);
            return memory;
        }, (key, val) -> {
            val.setAttestedCredentialData(credentialData);
            return val;
        });
    }

    /**
     * Saves decoded {@link AuthenticatorAttestationResponse} for the registration ceremony with the
     * given id.
     *
     * @param registrationCeremonyId given registration ceremony id
     * @param response               decoded {@link AuthenticatorAttestationResponse}
     */
    public void setAuthenticatorAttestationResponse(RegistrationCeremonyId registrationCeremonyId, AuthenticatorAttestationResponse response) {
        saveEntry(registrationCeremonyId, key -> {
            RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
            memory.setAttestationResponse(response);
            return memory;
        }, (key, val) -> {
            val.setAttestationResponse(response);
            return val;
        });
    }

    /**
     * Saves decoded {@link PublicKeyCredentialCreationResponse} for the registration ceremony with the
     * given id.
     *
     * @param id       given registration ceremony id
     * @param response decoded {@link PublicKeyCredentialCreationResponse}
     */
    public void setPublicKeyCredentialResponse(RegistrationCeremonyId id, PublicKeyCredentialCreationResponse response) {
        saveEntry(id, k -> {
            RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
            memory.setCredentialResponse(response);
            return memory;
        }, (key, val) -> {
            val.setCredentialResponse(response);
            return val;
        });
    }

    /**
     * Finds {@link CollectedClientData} by given registration ceremony id.
     *
     * @param id given registration ceremony id.
     * @return {@link CollectedClientData}
     */
    public CollectedClientData getClientData(RegistrationCeremonyId id) {
        return MEMORY.get(id).getClientData();
    }

    /**
     * Finds {@link PublicKeyCredentialCreationOptions} by registration ceremony id.
     *
     * @param id given registration ceremony id
     * @return {@link PublicKeyCredentialCreationOptions}
     */
    public PublicKeyCredentialCreationOptions getCredentialOptions(RegistrationCeremonyId id) {
        return MEMORY.get(id).getOptions();
    }

    /**
     * Finds {@link AuthenticatorAttestationResponse} by registration ceremony id.
     *
     * @param id given registration ceremony id
     * @return {@link AuthenticatorAttestationResponse}
     */
    public AuthenticatorAttestationResponse getAuthenticatorAttestationResponse(RegistrationCeremonyId id) {
        return MEMORY.get(id).getAttestationResponse();
    }

    /**
     * Finds {@link AttestedCredentialData} by registration ceremony id.
     *
     * @param id given registration ceremony id
     * @return {@link AttestedCredentialData}
     */
    public AttestedCredentialData getAttestedCredentialData(RegistrationCeremonyId id) {
        return MEMORY.get(id).getAttestedCredentialData();
    }

    /**
     * Finds clientDataJson by registration ceremony id.
     *
     * @param id given registration ceremony id
     * @return clientDataJson
     */
    public String getJsonText(RegistrationCeremonyId id) {
        return MEMORY.get(id).getJsonText();
    }

    /**
     * Finds clientDataHash by registration ceremony id.
     *
     * @param id given registration ceremony id
     * @return clientDataHash
     */
    public byte[] getClientDataHash(RegistrationCeremonyId id) {
        return MEMORY.get(id).getClientDataHash();
    }

    /**
     * Finds {@link PublicKeyCredentialCreationResponse}
     * by given registration ceremony id.
     *
     * @param id given registration ceremony id
     * @return {@link PublicKeyCredentialCreationResponse}
     */
    public PublicKeyCredentialCreationResponse getCredentialCreationResponse(RegistrationCeremonyId id) {
        return MEMORY.get(id).getCredentialResponse();
    }

    public void saveEntry(RegistrationCeremonyId id,
                          Function<RegistrationCeremonyId, RegistrationCeremonyInMemory> keyNoPresentFunction,
                          BiFunction<RegistrationCeremonyId,
                                  RegistrationCeremonyInMemory,
                                  RegistrationCeremonyInMemory> keyPresentFunction) {
        if (MEMORY.containsKey(id)) {
            MEMORY.computeIfPresent(id, keyPresentFunction);
        } else {
            MEMORY.computeIfAbsent(id, keyNoPresentFunction);
        }
    }
}
