package com.iwmnetwork.aqtos.internship.identify.api.commands;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Register fido user with the given attributes if all steps passed successfully command.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FidoUserRegistrationFinishCommand extends AbstractCommand {
    @TargetAggregateIdentifier
    private UserId userId;
    private String name;
    private String username;
    private byte[] publicKey;
    private byte[] credentialId;
    private int signCount;

}
