package com.fido2_proof_of_concepts.identify.model.aggregate;

import com.fido2_proof_of_concepts.identify.api.commands.registration_ceremony.*;
import com.fido2_proof_of_concepts.identify.api.events.*;
import com.fido2_proof_of_concepts.identify.api.events.registration_ceremony.*;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.model.exceptions.Fido2Exception;
import com.fido2_proof_of_concepts.identify.model.identifiers.RegistrationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.RegistrationCeremonyInMemoryRepository;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.*;
import com.fido2_proof_of_concepts.identify.repository.webauthn.crypto.*;
import com.fido2_proof_of_concepts.identify.repository.webauthn.enumerations.AttestationConveyancePreference;
import com.fido2_proof_of_concepts.identify.repository.webauthn.enumerations.UserVerificationRequirements;
import com.fido2_proof_of_concepts.identify.repository.webauthn.exceptions.VerificationFailedException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.boot.configurationprocessor.json.JSONException;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.UUID;

/**
 * Registration Ceremony Aggregate.
 */
@Aggregate(repository = "axonRegistrationCeremonyRepository")
@Getter
@Entity
@NoArgsConstructor
public class RegistrationCeremony {

    @AggregateIdentifier
    @EmbeddedId
    private RegistrationCeremonyId id;

    private static final String TYPE = "webauthn.create";

    @Transient
    RegistrationCeremonyInMemoryRepository repository = new RegistrationCeremonyInMemoryRepository();

    /**
     * Create {@link PublicKeyCredentialCreationOptions}
     * step 1 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for creating {@link PublicKeyCredentialCreationOptions}
     */
    @CommandHandler
    public RegistrationCeremony(CreatePublicKeyCredentialsOptionsCommand cmd) {
        PublicKeyCredentialsOptionsCreatedEvent event = new PublicKeyCredentialsOptionsCreatedEvent(
                cmd.getId(),
                new PublicKeyCredentialCreationOptions(
                        cmd.getId(),
                        new PublicKeyCredentialRpEntity(Constants.RP_ID),
                        new PublicKeyCredentialUserEntity(UUID.randomUUID().toString(), cmd.getUsername()),
                        cmd.getChallenge(),
                        new PublicKeyCredentialParameters(),
                        Constants.TIMEOUT,
                        new AuthenticatorSelectionCriteria(UserVerificationRequirements.DISCOURAGED.requirement),
                        AttestationConveyancePreference.DIRECT.attestationConveyancePreference
                )
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public String on(PublicKeyCredentialsOptionsCreatedEvent e) {
        this.id = (RegistrationCeremonyId) e.getCeremonyId();
        repository.setOptions(this.id, e.getPublicKeyCredentialCreationOptions());
        return id.getId();
    }

    /**
     * Verify that the received credential#response is instance of {@link AuthenticatorAttestationResponse}
     * step 3-5 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying that the received response is {@link AuthenticatorAttestationResponse}
     */
    @CommandHandler
    public void handle(FidoRegistrationStartCommand cmd) {
        try {
            DecodedAttestationResponseEvent event = new DecodedAttestationResponseEvent(
                    cmd.getCeremonyId(),
                    CBORDecoder.decodeAttStmt(cmd.getAttestationObject()),
                    cmd.getClientDataHash()
            );
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (Fido2Exception o_O) {
            throw new VerificationFailedException();
        }
    }

    public void on(DecodedAttestationResponseEvent event) {
        if (event.getCeremonyId().equals(id)) {
            repository.setAuthenticatorAttestationResponse(
                    id, event.getAttestationResponse());
            try {
                repository.setJsonText(id, Utf8Decoder
                        .bytesToUtf8String(JsonParser.JsonToByteArray(event.getClientDataJson())));
            } catch (JSONException o_O) {
                throw new Fido2Exception(o_O);
            }
        }
    }

    /**
     * Deserialize received clientDataJson text to {@link CollectedClientData}.
     * step 6 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for deserializing the received clientDataJsonText
     */
    @CommandHandler
    public void handle(DecodeJsonClientDataCommand cmd) {
        try {
            CollectedClientData clientData = JsonParser.getClientData(cmd.getClientDataJson());
            ClientDataDecodedEvent event = new ClientDataDecodedEvent(cmd.getCeremonyId(), clientData);
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (JSONException o_O) {
            throw new Fido2Exception(o_O);
        }
    }

    public void on(ClientDataDecodedEvent event) {
        if (event.getCeremonyId().equals(id)) {
            repository.setClientCollectedData(id, event.getClientData());
        }
    }

    /**
     * Verify {@link CollectedClientData}#type that equals to webauthn.create
     * step 7 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying value of deserialized client data type is webauth.create
     */
    @CommandHandler
    public void handle(VerifyClientDataTypeCommand cmd) {
        CollectedClientData clientData = repository.getClientData(id);
        boolean verified = clientData.getType().equals(TYPE);
        ClientDataTypeVerifiedEvent event = new ClientDataTypeVerifiedEvent(
                cmd.getCeremonyId(),
                verified);
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(ClientDataTypeVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify {@link CollectedClientData}#challenge that equals to the one we send in
     * {@link PublicKeyCredentialCreationOptions}
     * step 8 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying the challenge we issued is the same as the one we received
     */
    @CommandHandler
    public void handle(VerifyChallengeCommand cmd) {
        CollectedClientData clientData = repository.getClientData(id);
        PublicKeyCredentialCreationOptions options = repository.getCredentialOptions(id);
        boolean verified = options.getChallenge().equals(clientData.getChallenge());
        ChallengeVerifiedEvent event = new ChallengeVerifiedEvent(cmd.getCeremonyId(),
                verified);
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(ChallengeVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify {@link CollectedClientData}#origin that equals to our relying party defined origin
     * step 9 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying the received origin as our.
     */
    @CommandHandler
    public void handle(VerifyRpIdCommand cmd) {
        CollectedClientData clientData = repository.getClientData(id);
        boolean verified = clientData.getOrigin().equals(Constants.RP_ID_ORIGIN);
        RpIdVerifiedEvent event = new RpIdVerifiedEvent(
                cmd.getCeremonyId(),
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
     * Compute hash over the credential#response#clientDataJson using SHA-256
     * step 11 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for computing hash over the received clientDataJson
     */
    @CommandHandler
    public void handle(ComputeHashOverClientDataJsonCommand cmd) {
        try {
            String jsonText = repository.getJsonText(id);
            ComputedHashOverClientDataJsonEvent event = new ComputedHashOverClientDataJsonEvent(
                    cmd.getCeremonyId(),
                    SHA256.hashClientData(jsonText.getBytes())
            );
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (NoSuchAlgorithmException o_O) {
            throw new Fido2Exception(o_O);
        }
    }

    public void on(ComputedHashOverClientDataJsonEvent event) {
        if (id.equals(event.getCeremonyId())) {
            repository.setClientDataHash(id, event.getClientDataHash());
        }
    }

    /**
     * Perform Cbor decoding over attestationResponse to obtain auth data, and the attestation
     * statement object
     * step 12 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for performing cbor decoding over attestationResponse object
     */
    @CommandHandler
    public void handle(PerformCborDecodingOnAttestationResponseObjectCommand cmd) {
        AuthenticatorAttestationResponse attestationResponse = repository.getAuthenticatorAttestationResponse(id);
        AttestedCredentialData attestedCredentialData = AttestationResponseReader
                .decodeAttestedCredentialData(attestationResponse.getAuthData());
        CborDecodingPerformedOnResponseObjectEvent event = new CborDecodingPerformedOnResponseObjectEvent(
                cmd.getCeremonyId(),
                attestedCredentialData
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(CborDecodingPerformedOnResponseObjectEvent event) {
        if (id.equals(event.getCeremonyId())) {
            repository.setAttestedCredentialData(id, event.getAttestedCredentialData());
        }
    }

    /**
     * Verify that received Relying Party hashed id is the same as our Relying Party id hash
     * step 13 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param command command for verifying the rpId hash from auth data in attestation response
     */
    @CommandHandler
    public void handle(VerifyRpIdHashInAuthDataCommand command) {
        AuthenticatorAttestationResponse attestationResponse = repository.getAuthenticatorAttestationResponse(id);
        boolean verified = RelyingPartyUtils.verifyRrIdHash(attestationResponse.getAuthData(),
                Constants.RP_ID.getBytes());
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
     * step 14 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     * more on <a href="https://www.w3.org/TR/webauthn/#authenticator-data">...</a>
     *
     * @param cmd command for verifying if user is present in auth data flags
     */
    @CommandHandler
    public void handle(VerifyThatUserPresentInAuthDataIsSetCommand cmd) {
        AuthenticatorAttestationResponse response = repository
                .getAuthenticatorAttestationResponse((RegistrationCeremonyId) cmd.getCeremonyId());
        boolean verified = RelyingPartyUtils.verifyUserPresent(response.getAuthData());
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
     * step 15 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     * more on <a href="https://www.w3.org/TR/webauthn/#authenticator-data">...</a>
     *
     * @param cmd command for verifying if user is present in auth data flags
     */
    @CommandHandler
    public void handle(VerifyThatUserVerifiedInAuthDataIsSetCommand cmd) {
        AuthenticatorAttestationResponse response =
                repository.getAuthenticatorAttestationResponse((RegistrationCeremonyId) cmd.getCeremonyId());
        boolean verified = RelyingPartyUtils.verifyUserVerified(response.getAuthData());
        UserVerifiedInAuthDataIsSetEvent event = new UserVerifiedInAuthDataIsSetEvent(
                cmd.getCeremonyId(),
                verified);
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(UserVerifiedInAuthDataIsSetEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify that the alg parameter received from credential public key in authData matches our supported alg
     * step 16 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying the received alg parameter from credential public key
     */
    @CommandHandler
    public void handle(VerifyAlgParameterInAuthDataCommand cmd) {
        AttestedCredentialData attestedCredentialData = repository.getAttestedCredentialData(id);
        PublicKeyCredentialCreationOptions options = repository.getCredentialOptions(id);
        PublicKey pk = CBORDecoder
                .decodePublicKey(attestedCredentialData.getCredentialPublicKey());
        boolean verified = pk.getAlg() == options.getPubKeyCredParams().getAlg();
        AlgParameterInAuthDataVerifiedEvent event = new AlgParameterInAuthDataVerifiedEvent(
                cmd.getCeremonyId(), verified
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(AlgParameterInAuthDataVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify that received attestation response conveys valid signature
     * step 19 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying the attestation response signature.
     */
    @CommandHandler
    public void handle(VerifyAttStmtSignatureCommand cmd) {
        AuthenticatorAttestationResponse attestationResponse = repository.getAuthenticatorAttestationResponse(id);
        boolean verified;
        try {
            byte[] clientDataHash = repository.getClientDataHash(id);
            verified = SHA256.verifySignature(attestationResponse, clientDataHash);
            AttStmtSignatureVerifiedEvent event = new AttStmtSignatureVerifiedEvent(
                    cmd.getCeremonyId(),
                    verified
            );
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (CertificateException |
                 NoSuchAlgorithmException |
                 InvalidKeyException |
                 SignatureException e) {
            throw new Fido2Exception(e);
        }
    }

    public void on(AttStmtSignatureVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }
}
