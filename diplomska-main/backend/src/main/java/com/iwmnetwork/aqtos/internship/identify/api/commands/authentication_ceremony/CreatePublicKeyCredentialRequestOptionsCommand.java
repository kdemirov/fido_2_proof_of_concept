package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.RoutingKey;

/**
 * Create public key credential request options command.
 */
@AllArgsConstructor
@Getter
public class CreatePublicKeyCredentialRequestOptionsCommand extends AbstractCommand {

    @RoutingKey
    private AuthenticationCeremonyId authenticationCeremonyId;
}
