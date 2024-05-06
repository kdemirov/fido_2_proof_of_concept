package com.iwmnetwork.aqtos.internship.identify.model.aggregate;

import co.nstant.in.cbor.CborException;
import com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony.*;
import com.iwmnetwork.aqtos.internship.identify.api.events.*;
import com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony.*;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.exceptions.Fido2Exception;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.AuthenticationCeremonyInMemoryRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAssertionResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto.*;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions.VerificationFailedException;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.UUID;


/**
 * Authentication ceremony aggregate.
 */
@Aggregate(repository = "axonAuthenticationCeremonyRepository")
@Entity
@NoArgsConstructor
public class AuthenticationCeremony {

    @EmbeddedId
    private AuthenticationCeremonyId id;

    private static final String TYPE = "webauthn.get";

    @Autowired
    @Transient
    private AuthenticationCeremonyInMemoryRepository repository = new AuthenticationCeremonyInMemoryRepository();

    /**
     * Create {@link PublicKeyCredentialRequestOptions}
     * step 1 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param cmd command for creating {@link PublicKeyCredentialRequestOptions}
     */
    @CommandHandler
    public AuthenticationCeremony(CreatePublicKeyCredentialRequestOptionsCommand cmd) {
        PublicKeyCredentialRequestOptionsCreatedEvent event = new PublicKeyCredentialRequestOptionsCreatedEvent(
                cmd.getAuthenticationCeremonyId(),
                new PublicKeyCredentialRequestOptions(
                        UUID.randomUUID().toString(),
                        Constants.TIMEOUT,
                        cmd.getAuthenticationCeremonyId()
                )
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(PublicKeyCredentialRequestOptionsCreatedEvent event) {
        this.id = (AuthenticationCeremonyId) event.getAuthenticationCeremonyId();
        this.repository.saveOptions(id, event.getOptions());
    }

    /**
     * Verify that the received credential#response is instance of {@link AuthenticatorAssertionResponse}
     * step 3,8,9 from <a href="href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param cmd command for verifying that the received response is {@link AuthenticatorAssertionResponse}
     */
    @CommandHandler
    public void handle(FidoUserAuthenticationStartCommand cmd) {
        AssertionResponseDecodedEvent event = new AssertionResponseDecodedEvent(
                cmd.getCeremonyId(),
                CBORDecoder.decodeAssObj(cmd.getAuthData(),
                        cmd.getUserHandle(),
                        cmd.getSignature()),
                cmd.getId(),
                cmd.getClientDataJSON()
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(AssertionResponseDecodedEvent event) {
        if (event.getCeremonyId().equals(id)) {
            repository.saveResponse(id, event.getResponse());
            repository.saveCredentialId(id, event.getCredentialId());
            try {
                repository.saveClientDataJsonText(id, Utf8Decoder.bytesToUtf8String(
                        JsonParser.JsonToByteArray(event.getClientDataJson())));
            } catch (JSONException o_O) {
                throw new RuntimeException(o_O);
            }
        }
    }

    /**
     * Verify that the user trying to authenticate exists in our system by credential id.
     * step 6,7 from <a href="href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param command command for verifying that credential id exists.
     */
    @CommandHandler
    public void handle(VerifyThatCredentialIdExistsCommand command) {
        CredentialExistsEvent event = new CredentialExistsEvent(
                command.getCeremonyId(),
                command.isVerified(),
                command.getFidoUser()
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(CredentialExistsEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        } else {
            repository.saveFidoUser((AuthenticationCeremonyId) event.getCeremonyId(),
                    event.getFidoUser());
        }
    }

    /**
     * Deserialize received clientDataJson text to {@link CollectedClientData}.
     * step 10 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param cmd command for deserializing the received clientDataJsonText
     */
    @CommandHandler
    public void handle(DeserializeClientDataJsonCommand cmd) {
        String clientDataJson = repository.getClientDataJsonText((AuthenticationCeremonyId) cmd.getCeremonyId());
        try {
            ClientDataDeserializedEvent event = new ClientDataDeserializedEvent(
                    cmd.getCeremonyId(),
                    JsonParser.getClientData(clientDataJson)
            );
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (JSONException o_O) {
            throw new RuntimeException(o_O);
        }
    }

    public void on(ClientDataDeserializedEvent event) {
        if (id.equals(event.getCeremonyId())) {
            repository.saveClientData((AuthenticationCeremonyId) event.getCeremonyId(), event.getClientData());
        }
    }

    /**
     * Verify {@link CollectedClientData}#type that equals to webauthn.get
     * step 11 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param command command for verifying value of deserialized client data type is webauth.get
     */
    @CommandHandler
    public void handle(AuthenticationVerifyClientDataTypeCommand command) {
        CollectedClientData clientData = repository.getClientData((AuthenticationCeremonyId) command.getCeremonyId());
        boolean verified = clientData.getType().equals(TYPE);
        ClientDataTypeVerifiedEvent event = new ClientDataTypeVerifiedEvent(
                command.getCeremonyId(),
                verified
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(ClientDataTypeVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify {@link CollectedClientData}#challenge that equals to {@link PublicKeyCredentialRequestOptions}#challenge
     * step 12 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param command command for verifying the challenge issued in credential request response and the one we
     *                received from collected client data
     */
    @CommandHandler
    public void handle(AuthenticationVerifyChallengeCommand command) {
        CollectedClientData clientData = repository.getClientData((AuthenticationCeremonyId) command.getCeremonyId());
        PublicKeyCredentialRequestOptions options = repository.getOptions((AuthenticationCeremonyId) command.getCeremonyId());
        boolean verified = clientData.getChallenge().equals(options.getChallenge());
        ChallengeVerifiedEvent event = new ChallengeVerifiedEvent(
                command.getCeremonyId(),
                verified
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(ChallengeVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify {@link CollectedClientData}#origin matches our RP origin
     * step 13 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param command command for verifying value received origin in our origin
     */
    @CommandHandler
    public void handle(AuthenticationVerifyRpIdCommand command) {
        CollectedClientData clientData = repository.getClientData((AuthenticationCeremonyId) command.getCeremonyId());
        boolean verified = clientData.getOrigin().equals(Constants.RP_ID_ORIGIN);
        RpIdVerifiedEvent event = new RpIdVerifiedEvent(
                command.getCeremonyId(),
                verified
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(RpIdVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify {@link CollectedClientData}#origin matches our RP origin
     * step 15 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param command command for verifying value received origin in our origin
     */
    @CommandHandler
    public void handle(AuthenticationVerifyRpIdHashInAuthDataCommand command) {
        byte[] authData = repository.getResponse((AuthenticationCeremonyId) command.getCeremonyId()).getAuthenticatorData();
        boolean verified = RelyingPartyUtils.verifyRrIdHash(authData, Constants.RP_ID.getBytes());
        RpIdHashInAuthDataVerifiedEvent event = new RpIdHashInAuthDataVerifiedEvent(
                command.getCeremonyId(),
                verified
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(RpIdHashInAuthDataVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify if user is present in received authData from flags exactly if the first bit is set
     * step 16 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     * more on <a href="https://www.w3.org/TR/webauthn/#authenticator-data">...</a>
     *
     * @param cmd command for verifying if user is present in auth data flags
     */
    @CommandHandler
    public void handle(AuthenticationVerifyThatUserPresentInAuthDataIsSetCommand cmd) {
        byte[] authData = repository.getResponse((AuthenticationCeremonyId) cmd.getCeremonyId()).getAuthenticatorData();
        boolean verified = RelyingPartyUtils.verifyUserPresent(authData);
        UserPresentInAuthDataVerifiedEvent event = new UserPresentInAuthDataVerifiedEvent(
                cmd.getCeremonyId(),
                verified
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(UserPresentInAuthDataVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify if user verified flag in received authData is set from flags exactly if the second bit is set
     * step 17 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     * more on <a href="https://www.w3.org/TR/webauthn/#authenticator-data">...</a>
     *
     * @param cmd command for verifying if user is present in auth data flags
     */
    @CommandHandler
    public void handle(AuthenticationVerifyThatUserVerifiedInAuthDataIsSetCommand cmd) {
        byte[] authData = repository.getResponse((AuthenticationCeremonyId) cmd.getCeremonyId()).getAuthenticatorData();
        boolean verified = RelyingPartyUtils.verifyUserVerified(authData);
        UserVerifiedInAuthDataIsSetEvent event = new UserVerifiedInAuthDataIsSetEvent(
                cmd.getCeremonyId(),
                verified
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(UserVerifiedInAuthDataIsSetEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Compute hash over received client data json.
     * step 19 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param cmd command for computing hash over received client data json
     */
    @CommandHandler
    public void handle(AuthenticationComputeHashOverClientDataJsonCommand cmd) {
        String jsonText = repository.getClientDataJsonText(id);
        try {
            ComputedHashOverClientDataJsonEvent event = new ComputedHashOverClientDataJsonEvent(
                    cmd.getCeremonyId(),
                    SHA256.hashClientData(jsonText.getBytes())
            );
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (NoSuchAlgorithmException o_O) {
            throw new RuntimeException(o_O);
        }
    }

    public void on(ComputedHashOverClientDataJsonEvent event) {
        if (id.equals(event.getCeremonyId())) {
            this.repository.saveClientDataHash(id, event.getClientDataHash());
        }
    }

    /**
     * Verify that received assertion response signature is valid signature over the binary concatenation
     * of authData and hashed client data
     * step 20 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param command command for verified if the signature from response is valid
     */
    @CommandHandler
    public void handle(VerifyAssertionResponseSignatureCommand command) {
        AuthenticatorAssertionResponse response = repository.getResponse(id);
        byte[] clientDataHash = repository.getClientDataHash(id);
        byte[] publicKey = repository.getPublicKey(id);
        boolean verified;
        try {
            verified = SHA256.verifyAssertionSignature(response, clientDataHash, publicKey);
            AssertionResponseSignatureVerifiedEvent event = new AssertionResponseSignatureVerifiedEvent(
                    command.getCeremonyId(),
                    verified
            );
            this.on(event);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException |
                 SignatureException | CborException | InvalidParameterSpecException o_O) {
            throw new Fido2Exception(o_O);
        }
    }

    public void on(AssertionResponseSignatureVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify that the value received from auth data for stored sign count is valid
     * if the value is less that the one we stored there is chance that cloned authenticator is in
     * use
     * step 21 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     *
     * @param cmd command for verified if the signature count value from auth data  is valid
     */
    @CommandHandler
    public void handle(VerifyTheValueOfStoredSignatureCountCommand cmd) {
        byte[] authData = repository.getResponse(id).getAuthenticatorData();
        int signatureCount = RelyingPartyUtils.getSignCount(authData);
        boolean verified = signatureCount > cmd.getStoredSignatureCount();
        ValueOfStoredSignatureCountVerifiedEvent event = new ValueOfStoredSignatureCountVerifiedEvent(
                cmd.getCeremonyId(),
                verified,
                signatureCount
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(ValueOfStoredSignatureCountVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }
}
