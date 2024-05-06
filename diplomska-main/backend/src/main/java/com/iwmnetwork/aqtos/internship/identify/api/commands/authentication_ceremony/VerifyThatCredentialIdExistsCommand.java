package com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCeremonyCommand;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.Getter;

/**
 * Verify that credential id received from credential request options exists in our system.
 */
@Getter
public class VerifyThatCredentialIdExistsCommand extends AbstractCeremonyCommand {

    private final boolean verified;
    private final byte[] publicKey;

    /**
     * Constructor.
     *
     * @param authenticationCeremonyId authentication ceremony id for authentication ceremony aggregate
     * @param publicKey                public key of the user with the credential id.
     */
    public VerifyThatCredentialIdExistsCommand(AuthenticationCeremonyId authenticationCeremonyId,
                                               boolean verified,
                                               byte[] publicKey) {
        super(authenticationCeremonyId);
        this.verified = verified;
        this.publicKey = publicKey;
    }
}
