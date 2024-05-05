package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.CreatePublicKeyCredentialsOptionsCommand;
import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractEvent;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.*;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations.AttestationConveyancePreference;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations.UserVerificationRequirements;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
