package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
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
