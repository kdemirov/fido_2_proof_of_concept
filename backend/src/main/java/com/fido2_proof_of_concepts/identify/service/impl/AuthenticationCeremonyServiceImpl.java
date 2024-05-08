package com.fido2_proof_of_concepts.identify.service.impl;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony.CreatePublicKeyCredentialRequestOptionsCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.AuthenticationCeremonyInMemoryRepository;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import com.fido2_proof_of_concepts.identify.service.AuthenticationCeremonyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation for authentication ceremony service.
 */
@RequiredArgsConstructor
@Service
public class AuthenticationCeremonyServiceImpl implements AuthenticationCeremonyService {

    private final DefaultService defaultIdentifyService;
    private final AuthenticationCeremonyInMemoryRepository repository;

    @Override
    public PublicKeyCredentialRequestOptions createRequestOptions() {
        AuthenticationCeremonyId id = new AuthenticationCeremonyId();
        CreatePublicKeyCredentialRequestOptionsCommand cmd = new CreatePublicKeyCredentialRequestOptionsCommand(
                id
        );
        defaultIdentifyService.dispatch(cmd);

        return repository.getOptions(id);
    }
}
