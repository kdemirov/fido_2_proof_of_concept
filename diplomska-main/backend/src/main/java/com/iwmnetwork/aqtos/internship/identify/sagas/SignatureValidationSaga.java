package com.iwmnetwork.aqtos.internship.identify.sagas;

import com.iwmnetwork.aqtos.internship.identify.api.commands.UpdateFidoUserSignCountCommand;
import com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony.ValueOfStoredSignatureCountVerifiedEvent;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.AuthenticationCeremonyInMemoryRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.FidoUserRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Saga
public class SignatureValidationSaga {

    private final UserService userService;
    private final FidoUserRepository fidoUserRepository;
    private final AuthenticationCeremonyInMemoryRepository repository;
    private final DefaultIdentifyService defaultIdentifyService;

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
