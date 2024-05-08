package com.fido2_proof_of_concepts.identify.sagas;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.UpdateFidoUserSignCountCommand;
import com.fido2_proof_of_concepts.identify.api.events.authentication_ceremony.ValueOfStoredSignatureCountVerifiedEvent;
import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.model.aggregate.User;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.AuthenticationCeremonyInMemoryRepository;
import com.fido2_proof_of_concepts.identify.repository.FidoUserRepository;
import com.fido2_proof_of_concepts.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.fido2_proof_of_concepts.identify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Signature counter saga.
 */
@RequiredArgsConstructor
@Saga
public class SignatureCounterSaga {

    private final UserService userService;
    private final FidoUserRepository fidoUserRepository;
    private final AuthenticationCeremonyInMemoryRepository repository;
    private final DefaultService defaultIdentifyService;

    /**
     * Update the value of stored signature for fido user saga.
     *
     * @param event {@link ValueOfStoredSignatureCountVerifiedEvent}
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "ceremonyId")
    public void handle(ValueOfStoredSignatureCountVerifiedEvent event) {
        byte[] credentialId = repository.getCredentialId((AuthenticationCeremonyId) event.getCeremonyId());
        FidoUser fidoUser = fidoUserRepository.findByCredentialId(credentialId)
                .orElseThrow(VerificationFailedException::new);
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
