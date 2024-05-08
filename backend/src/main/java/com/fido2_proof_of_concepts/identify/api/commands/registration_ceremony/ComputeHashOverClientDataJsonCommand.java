package com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony;


import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Compute Hash Over Received Client Data command;
 */
@AllArgsConstructor
@Getter
public class ComputeHashOverClientDataJsonCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param registrationCeremonyId command for registration ceremony aggregate with the given id.
     */
    public ComputeHashOverClientDataJsonCommand(Identifier registrationCeremonyId) {
        super(registrationCeremonyId);
    }
}
