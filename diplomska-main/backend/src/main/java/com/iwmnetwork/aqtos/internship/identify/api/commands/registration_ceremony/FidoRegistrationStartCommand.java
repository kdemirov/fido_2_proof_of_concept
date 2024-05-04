package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * FidoRegistrationStartCommand.
 */
@AllArgsConstructor
@Getter
public class FidoRegistrationStartCommand extends AbstractRegistrationCeremonyCommand {
    private String id;
    private String type;
    private String attestationObject;
    private String username;
    private String clientDataHash;

    /**
     * Constructor.
     *
     * @param registrationCeremonyId command for registration ceremony with the given id.
     * @param id                     credentialId
     * @param type                   type webauthn.create
     * @param attestationObject      attestation object in json
     * @param username               username
     * @param clientDataHash         clientData received in json
     */
    public FidoRegistrationStartCommand(RegistrationCeremonyId registrationCeremonyId,
                                        String id,
                                        String type,
                                        String attestationObject,
                                        String username,
                                        String clientDataHash) {
        super(registrationCeremonyId);
        this.id = id;
        this.type = type;
        this.attestationObject = attestationObject;
        this.username = username;
        this.clientDataHash = clientDataHash;
    }
}
