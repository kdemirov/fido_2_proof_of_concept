package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.Getter;

/**
 * Verify that credential id received from credential request options exists in our system.
 */
@Getter
public class VerifyThatCredentialIdExistsCommand extends AbstractCeremonyCommand {

    private final boolean verified;
    private final FidoUser fidoUser;

    /**
     * Constructor.
     *
     * @param authenticationCeremonyId authentication ceremony id for authentication ceremony aggregate
     * @param fidoUser                 fido user.
     */
    public VerifyThatCredentialIdExistsCommand(AuthenticationCeremonyId authenticationCeremonyId,
                                               boolean verified,
                                               FidoUser fidoUser) {
        super(authenticationCeremonyId);
        this.verified = verified;
        this.fidoUser = fidoUser;
    }
}
