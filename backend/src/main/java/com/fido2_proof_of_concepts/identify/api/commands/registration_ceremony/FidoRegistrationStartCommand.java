package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FidoRegistrationStartCommand.
 */
@AllArgsConstructor
@Getter
public class FidoRegistrationStartCommand extends AbstractCeremonyCommand {
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
