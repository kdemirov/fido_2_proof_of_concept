package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.CollectedClientData;
import lombok.Getter;

/**
 * Decode Json Client Data Command.
 */
@Getter
public class DecodeJsonClientDataCommand extends AbstractCeremonyCommand {

    private final String clientDataJson;

    /**
     * Constructor.
     *
     * @param id             id for registration ceremony aggregate.
     * @param clientDataJson clientDataJson for deserializing in our model {@link CollectedClientData}
     */
    public DecodeJsonClientDataCommand(RegistrationCeremonyId id, String clientDataJson) {
        super(id);
        this.clientDataJson = clientDataJson;
    }
}
