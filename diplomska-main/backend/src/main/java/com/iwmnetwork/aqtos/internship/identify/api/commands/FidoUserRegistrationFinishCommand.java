package com.iwmnetwork.aqtos.internship.identify.api.commands;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FidoUserRegistrationFinishCommand {
    @TargetAggregateIdentifier
    private UserId userId;
    private String name;
    private String username;
    private byte[] publicKey;
    private byte[] credentialId;

}
