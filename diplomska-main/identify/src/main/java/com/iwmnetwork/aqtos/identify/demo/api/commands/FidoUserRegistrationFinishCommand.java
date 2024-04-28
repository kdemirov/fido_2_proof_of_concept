package com.iwmnetwork.aqtos.identify.demo.api.commands;

import com.iwmnetwork.aqtos.identify.demo.model.identifiers.UserId;
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
