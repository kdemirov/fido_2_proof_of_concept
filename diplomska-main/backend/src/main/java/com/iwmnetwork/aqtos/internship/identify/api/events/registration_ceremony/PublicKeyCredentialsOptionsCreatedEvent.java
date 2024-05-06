package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
import lombok.Getter;

/**
 * PublicKeyCredentialsOptionsCreated event.
 */
@Getter
public class PublicKeyCredentialsOptionsCreatedEvent extends AbstractCeremonyEvent {

    private final PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions;

    /**
     * Constructor.
     *
     * @param id      registration ceremony id for registration ceremony id
     * @param options created public key credentials options
     */
    public PublicKeyCredentialsOptionsCreatedEvent(RegistrationCeremonyId id,
                                                   PublicKeyCredentialCreationOptions options) {
        super(id);
        this.publicKeyCredentialCreationOptions = options;
    }
}
