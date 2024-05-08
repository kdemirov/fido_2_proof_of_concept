package com.fido2_proof_of_concepts.identify.api.commands;

import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.FidoUserId;
import com.fido2_proof_of_concepts.identify.model.identifiers.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Update fido user sign count command.
 */
@AllArgsConstructor
@Getter
public class UpdateFidoUserSignCountCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private final UserId userId;
    private final FidoUserId fidoUserId;
    private final int storedCount;
}
