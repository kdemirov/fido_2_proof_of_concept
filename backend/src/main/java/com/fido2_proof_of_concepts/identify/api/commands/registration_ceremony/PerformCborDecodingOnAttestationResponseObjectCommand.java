package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;

import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import lombok.Getter;

/**
 * Perform Cbor Decoding on Attestation Response Object Command.
 */
@Getter
public class PerformCborDecodingOnAttestationResponseObjectCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public PerformCborDecodingOnAttestationResponseObjectCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
