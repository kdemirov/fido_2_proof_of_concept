package com.iwmnetwork.aqtos.internship.identify.service.impl;

import com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony.CreatePublicKeyCredentialRequestOptionsCommand;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.AuthenticationCeremony;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.AuthenticationCeremonyInMemoryRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import com.iwmnetwork.aqtos.internship.identify.service.AuthenticationCeremonyService;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation for authentication ceremony service.
 */
@RequiredArgsConstructor
@Service
public class AuthenticationCeremonyServiceImpl implements AuthenticationCeremonyService {

    private final DefaultIdentifyService defaultIdentifyService;
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
