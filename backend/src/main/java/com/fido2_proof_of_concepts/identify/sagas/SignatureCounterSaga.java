package com.fido2_proof_of_concepts.identify.sagas;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.UpdateFidoUserSignCountCommand;
import com.fido2_proof_of_concepts.identify.api.events.authentication_ceremony.ValueOfStoredSignatureCountVerifiedEvent;
import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.model.aggregate.User;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.AuthenticationCeremonyInMemoryRepository;
import com.fido2_proof_of_concepts.identify.service.UserService;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Signature counter saga.
 */
@Saga
public class SignatureCounterSaga {

    @Autowired
    private UserService userService;
    private AuthenticationCeremonyInMemoryRepository repository = new AuthenticationCeremonyInMemoryRepository();
    @Autowired
    private DefaultService defaultIdentifyService;

    /**
     * Update the value of stored signature for fido user saga.
     *
     * @param event {@link ValueOfStoredSignatureCountVerifiedEvent}
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "ceremonyId")
    public void handle(ValueOfStoredSignatureCountVerifiedEvent event) {
        FidoUser fidoUser = repository.getFidoUser((AuthenticationCeremonyId) event.getCeremonyId());
        User user = userService.findByFidoUser(fidoUser).orElseThrow(() ->
                new UsernameNotFoundException(fidoUser.getFidoUserId().getId()));
        if (event.isVerified()) {
            UpdateFidoUserSignCountCommand cmd = new UpdateFidoUserSignCountCommand(
                    user.getId(),
                    fidoUser.getFidoUserId(),
                    event.getSignatureCount()
            );
            this.defaultIdentifyService.dispatch(cmd);
        }
    }
}
