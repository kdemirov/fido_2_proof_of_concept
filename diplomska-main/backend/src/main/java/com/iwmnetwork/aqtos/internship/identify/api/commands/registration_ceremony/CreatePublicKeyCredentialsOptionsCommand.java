package com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.RoutingKey;

/**
 * CreatePublicKeyCredentialsOptionsCommand.
 */
@AllArgsConstructor
@Getter
public class CreatePublicKeyCredentialsOptionsCommand extends AbstractCommand {
    @RoutingKey
    private RegistrationCeremonyId id;
    private String username;
    private String challenge;
}
