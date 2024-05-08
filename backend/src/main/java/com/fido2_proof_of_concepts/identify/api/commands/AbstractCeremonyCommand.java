package com.fido2_proof_of_concepts.identify.api.commands;

import com.fido2_proof_of_concepts.common.commands.AbstractCommand;
import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * AbstractRegistrationCeremony Command.
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class AbstractCeremonyCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    protected Identifier ceremonyId;
}
