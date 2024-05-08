package com.fido2_proof_of_concepts.identify.api.events.registration_ceremony;

import com.fido2_proof_of_concepts.identify.api.events.AbstractCeremonyEvent;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
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
