package com.iwmnetwork.aqtos.internship.identify.api.commands;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
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
