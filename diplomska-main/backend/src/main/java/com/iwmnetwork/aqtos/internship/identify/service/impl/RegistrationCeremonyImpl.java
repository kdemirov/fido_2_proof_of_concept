package com.iwmnetwork.aqtos.internship.identify.service.impl;

import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.CreatePublicKeyCredentialsOptionsCommand;
import com.iwmnetwork.aqtos.internship.identify.api.commands.FidoUserRegistrationFinishCommand;
import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.FidoUserRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.RegistrationCeremonyInMemoryRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto.RelyingPartyUtils;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import com.iwmnetwork.aqtos.internship.identify.service.RegistrationCeremonyService;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of registration ceremony service.
 */
@Service
@RequiredArgsConstructor
public class RegistrationCeremonyImpl implements RegistrationCeremonyService {

    private final DefaultIdentifyService defaultIdentifyService;
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
        RegistrationCeremonyId registrationCeremonyId = new RegistrationCeremonyId(cmd.getRegistrationCeremonyId());
        AttestedCredentialData attestedCredentialData = registrationCeremonyInMemoryRepository
                .getAttestedCredentialData(registrationCeremonyId);
        AuthenticatorAttestationResponse response = registrationCeremonyInMemoryRepository
                .getAuthenticatorAttestationResponse(registrationCeremonyId);
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
