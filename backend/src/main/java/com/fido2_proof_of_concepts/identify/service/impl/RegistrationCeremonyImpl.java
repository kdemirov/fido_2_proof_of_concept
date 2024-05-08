package com.fido2_proof_of_concepts.identify.service.impl;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.FidoUserRegistrationFinishCommand;
import com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony.CreatePublicKeyCredentialsOptionsCommand;
import com.fido2_proof_of_concepts.identify.model.aggregate.User;
import com.fido2_proof_of_concepts.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.FidoUserRepository;
import com.fido2_proof_of_concepts.identify.repository.RegistrationCeremonyInMemoryRepository;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
import com.fido2_proof_of_concepts.identify.repository.webauthn.crypto.RelyingPartyUtils;
import com.fido2_proof_of_concepts.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.fido2_proof_of_concepts.identify.service.RegistrationCeremonyService;
import com.fido2_proof_of_concepts.identify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of registration ceremony service.
 */
@Service
@RequiredArgsConstructor
public class RegistrationCeremonyImpl implements RegistrationCeremonyService {

    private final DefaultService defaultIdentifyService;
    private final RegistrationCeremonyInMemoryRepository registrationCeremonyInMemoryRepository;
    private final FidoUserRepository fidoUserRepository;
    private final UserService userService;

    public PublicKeyCredentialCreationOptions createOptions(String username) {
        RegistrationCeremonyId registrationCeremonyId = new RegistrationCeremonyId();
        CreatePublicKeyCredentialsOptionsCommand cmd = new CreatePublicKeyCredentialsOptionsCommand(
                registrationCeremonyId,
                username,
                UUID.randomUUID().toString());
        this.defaultIdentifyService.dispatch(cmd);

        return registrationCeremonyInMemoryRepository.getCredentialOptions(registrationCeremonyId);
    }

    public boolean registrationSuccessful(PublicKeyCredentialCreationResponse cmd) {
        AttestedCredentialData attestedCredentialData = registrationCeremonyInMemoryRepository
                .getAttestedCredentialData(cmd.getRegistrationCeremonyId());
        AuthenticatorAttestationResponse response = registrationCeremonyInMemoryRepository
                .getAuthenticatorAttestationResponse(cmd.getRegistrationCeremonyId());
        byte[] credentialId = attestedCredentialData.getCredentialId();
        byte[] publicKey = attestedCredentialData.getCredentialPublicKey();
        int signCount = RelyingPartyUtils.getSignCount(response.getAuthData());
        User user = userService.findByUsername(cmd.getUsername()).orElseThrow(VerificationFailedException::new);
        if (fidoUserRepository.existsByCredentialId(credentialId)) {
            return false;
        }
        this.defaultIdentifyService.dispatch(new FidoUserRegistrationFinishCommand(
                user.getId(),
                user.getName(),
                user.getUsername(),
                publicKey,
                credentialId,
                signCount
        ));
        return true;
    }
}
