package com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony;

import com.fido2_proof_of_concepts.identify.api.commands.AbstractCeremonyCommand;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import lombok.Getter;

/**
 * Fido user authentication start command.
 */
@Getter
public class FidoUserAuthenticationStartCommand extends AbstractCeremonyCommand {

    private final String id;
    private final String signature;
    private final String authData;
    private final String userHandle;
    private final String clientDataJSON;
    private final String type;

    /**
     * Constructor.
     *
     * @param authenticationCeremonyId authentication ceremony id
     * @param id                       credential id
     * @param signature                signature
     * @param authData                 auth data
     * @param userHandle               user handle
     * @param clientDataJSON           client data json
     * @param type                     type
     */
    public FidoUserAuthenticationStartCommand(AuthenticationCeremonyId authenticationCeremonyId,
                                              String id,
                                              String signature,
                                              String authData,
                                              String userHandle,
                                              String clientDataJSON,
                                              String type) {
        super(authenticationCeremonyId);
        this.id = id;
        this.signature = signature;
        this.authData = authData;
        this.userHandle = userHandle;
        this.clientDataJSON = clientDataJSON;
        this.type = type;
    }
}
