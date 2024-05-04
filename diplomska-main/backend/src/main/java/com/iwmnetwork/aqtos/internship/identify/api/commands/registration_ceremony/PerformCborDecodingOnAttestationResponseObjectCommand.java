package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Perform Cbor Decoding on Attestation Response Object Command.
 */
@Getter
public class PerformCborDecodingOnAttestationResponseObjectCommand extends AbstractRegistrationCeremonyCommand {

    /**
     * Constructor.
     *
     * @param id registration ceremony id for registration ceremony aggregate
     */
    public PerformCborDecodingOnAttestationResponseObjectCommand(RegistrationCeremonyId id) {
        super(id);
    }
}
