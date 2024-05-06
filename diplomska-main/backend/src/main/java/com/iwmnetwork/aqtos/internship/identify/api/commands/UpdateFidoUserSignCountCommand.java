package com.iwmnetwork.aqtos.internship.identify.api.commands;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
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
