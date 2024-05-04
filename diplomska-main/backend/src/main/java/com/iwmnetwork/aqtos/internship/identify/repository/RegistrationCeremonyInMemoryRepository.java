package com.iwmnetwork.aqtos.internship.identify.repository;

import com.iwmnetwork.aqtos.internship.identify.model.aggregate.RegistrationCeremony;
import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.iwmnetwork.aqtos.internship.identify.model.dto.RegistrationCeremonyInMemory;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

        if (MEMORY.containsKey(id)) {
            MEMORY.computeIfPresent(id, (key, val) -> {
                val.setOptions(options);
                return val;
            });
        } else {
            MEMORY.computeIfAbsent(id, key -> {
                RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
                memory.setOptions(options);
                return memory;
            });
        }
    }

    /**
     * Saves received clientJsonText for the registration ceremony with the
     * given id.
     *
     * @param id       given registration ceremony id
     * @param jsonText received clientDataJsonText
     */
    public void setJsonText(RegistrationCeremonyId id, String jsonText) {
        if (MEMORY.containsKey(id)) {
            MEMORY.computeIfPresent(id, (key, val) -> {
                val.setJsonText(jsonText);
                return val;
            });
        } else {
            MEMORY.computeIfAbsent(id, key -> {
                RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
                memory.setJsonText(jsonText);
                return memory;
            });
        }
    }

    /**
     * Saves hashed client data json text for the registration ceremony with the
     * given id.
     *
     * @param id             given registration ceremony id
     * @param clientDataHash hashed client data
     */
    public void setClientDataHash(RegistrationCeremonyId id, byte[] clientDataHash) {
        if (MEMORY.containsKey(id)) {
            MEMORY.computeIfPresent(id, (key, val) -> {
                val.setClientDataHash(clientDataHash);
                return val;
            });
        } else {
            MEMORY.computeIfAbsent(id, key -> {
                RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
                memory.setClientDataHash(clientDataHash);
                return memory;
            });
        }
    }

    /**
     * Saves deserialized {@link CollectedClientData} for the registration ceremony with the
     * given id.
     *
     * @param registrationCeremonyId given registration ceremony id
     * @param clientData             deserialized {@link CollectedClientData}
     */
    public void setClientCollectedData(RegistrationCeremonyId registrationCeremonyId, CollectedClientData clientData) {
        if (MEMORY.containsKey(registrationCeremonyId)) {
            MEMORY.computeIfPresent(registrationCeremonyId, (key, val) -> {
                val.setClientData(clientData);
                return val;
            });
        } else {
            MEMORY.computeIfAbsent(registrationCeremonyId, key -> {
                RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
                memory.setClientData(clientData);
                return memory;
            });
        }
    }

    /**
     * Saves decoded {@link AttestedCredentialData} for the registration ceremony with the
     * given id.
     *
     * @param registrationCeremonyId given registration ceremony id
     * @param credentialData         decoded {@link AttestedCredentialData}
     */
    public void setAttestedCredentialData(RegistrationCeremonyId registrationCeremonyId, AttestedCredentialData credentialData) {
        if (MEMORY.containsKey(registrationCeremonyId)) {
            MEMORY.computeIfPresent(registrationCeremonyId, (key, val) -> {
                val.setAttestedCredentialData(credentialData);
                return val;
            });
        } else {
            MEMORY.computeIfAbsent(registrationCeremonyId, key -> {
                RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
                memory.setAttestedCredentialData(credentialData);
                return memory;
            });
        }
    }

    /**
     * Saves decoded {@link AuthenticatorAttestationResponse} for the registration ceremony with the
     * given id.
     *
     * @param registrationCeremonyId given registration ceremony id
     * @param response               decoded {@link AuthenticatorAttestationResponse}
     */
    public void setAuthenticatorAttestationResponse(RegistrationCeremonyId registrationCeremonyId, AuthenticatorAttestationResponse response) {
        if (MEMORY.containsKey(registrationCeremonyId)) {
            MEMORY.computeIfPresent(registrationCeremonyId, (key, val) -> {
                val.setAttestationResponse(response);
                return val;
            });
        } else {
            MEMORY.computeIfAbsent(registrationCeremonyId, key -> {
                RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
                memory.setAttestationResponse(response);
                return memory;
            });
        }
    }

    /**
     * Saves decoded {@link PublicKeyCredentialCreationResponse} for the registration ceremony with the
     * given id.
     *
     * @param id       given registration ceremony id
     * @param response decoded {@link PublicKeyCredentialCreationResponse}
     */
    public void setPublicKeyCredentialResponse(RegistrationCeremonyId id, PublicKeyCredentialCreationResponse response) {
        if (MEMORY.containsKey(id)) {
            MEMORY.computeIfPresent(id, (key, val) -> {
                val.setCredentialResponse(response);
                return val;
            });
        } else {
            MEMORY.computeIfAbsent(id, k -> {
                RegistrationCeremonyInMemory memory = new RegistrationCeremonyInMemory();
                memory.setCredentialResponse(response);
                return memory;
            });
        }
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
}
