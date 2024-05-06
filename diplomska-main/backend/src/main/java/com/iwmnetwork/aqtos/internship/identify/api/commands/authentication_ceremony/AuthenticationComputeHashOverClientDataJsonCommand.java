package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Compute Hash Over Received Client Data command;
 */
@AllArgsConstructor
@Getter
public class AuthenticationComputeHashOverClientDataJsonCommand extends AbstractCeremonyCommand {

    /**
     * Constructor.
     *
     * @param authenticationId command for authentication ceremony aggregate with the given id.
     */
    public AuthenticationComputeHashOverClientDataJsonCommand(Identifier authenticationId) {
        super(authenticationId);
    }
}
