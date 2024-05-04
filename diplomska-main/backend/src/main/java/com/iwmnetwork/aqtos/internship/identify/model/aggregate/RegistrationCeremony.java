package com.iwmnetwork.aqtos.internship.identify.model.aggregate;

import com.iwmnetwork.aqtos.internship.identify.api.commands.registration_ceremony.*;
import com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony.*;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.exceptions.Fido2Exception;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.RegistrationCeremonyInMemoryRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.*;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto.CBORDecoder;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto.JsonParser;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto.SHA256;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.crypto.Utf8Decoder;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations.AttestationConveyancePreference;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.enumerations.UserVerificationRequirements;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions.VerificationFailedException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.boot.configurationprocessor.json.JSONException;

import javax.persistence.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Arrays;
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

    private static final String RP_ID_CROSS_ORIGIN = "http://localhost:3000";
    private static final String RP_ID = "localhost";

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
                        new PublicKeyCredentialRpEntity(Constants.rpId),
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
        this.id = e.getRegistrationCeremonyId();
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
                    cmd.getRegistrationCeremonyId(),
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
        if (event.getRegistrationCeremonyId().equals(id)) {
            repository.setAuthenticatorAttestationResponse(
                    event.getRegistrationCeremonyId(), event.getAttestationResponse());
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
            ClientDataDecodedEvent event = new ClientDataDecodedEvent(cmd.getRegistrationCeremonyId(), clientData);
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (JSONException o_O) {
            throw new Fido2Exception(o_O);
        }
    }

    public void on(ClientDataDecodedEvent event) {
        if (event.getRegistrationCeremonyId().equals(id)) {
            repository.setClientCollectedData(id, event.getClientData());
        }
    }

    /**
     * Verify from {@link CollectedClientData}#type that equals to webauthn.create
     * step 7 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying value of deserialized client data type is webauth.create
     */
    @CommandHandler
    public void handle(RegistrationVerifyClientDataTypeCommand cmd) {
        CollectedClientData clientData = repository.getClientData(id);
        boolean verified = clientData.getType().equals(TYPE);
        RegistrationClientDataTypeVerifiedEvent event = new RegistrationClientDataTypeVerifiedEvent(
                cmd.getRegistrationCeremonyId(),
                verified);
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(RegistrationClientDataTypeVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify from {@link CollectedClientData}#challenge that equals to the one we send in
     * {@link PublicKeyCredentialCreationOptions}
     * step 8 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying the challenge we issued is the same as the one we received
     */
    @CommandHandler
    public void handle(RegistrationVerifyChallengeCommand cmd) {
        CollectedClientData clientData = repository.getClientData(id);
        PublicKeyCredentialCreationOptions options = repository.getCredentialOptions(id);
        boolean verified = options.getChallenge().equals(clientData.getChallenge());
        RegistrationChallengeVerifiedEvent event = new RegistrationChallengeVerifiedEvent(cmd.getRegistrationCeremonyId(),
                verified);
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(RegistrationChallengeVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify from {@link CollectedClientData}#origin that equals to our relying party defined origin
     * step 9 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param cmd command for verifying the received origin as our.
     */
    @CommandHandler
    public void handle(RegistrationVerifyRpIdCommand cmd) {
        CollectedClientData clientData = repository.getClientData(id);
        boolean verified = clientData.getOrigin().equals(RP_ID_CROSS_ORIGIN);
        RegistrationRpIdVerifiedEvent event = new RegistrationRpIdVerifiedEvent(
                cmd.getRegistrationCeremonyId(),
                verified
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(RegistrationRpIdVerifiedEvent event) {
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
                    cmd.getRegistrationCeremonyId(),
                    SHA256.hashClientData(jsonText.getBytes())
            );
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (NoSuchAlgorithmException o_O) {
            throw new Fido2Exception(o_O);
        }
    }

    public void on(ComputedHashOverClientDataJsonEvent event) {
        if (id.equals(event.getRegistrationCeremonyId())) {
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
                cmd.getRegistrationCeremonyId(),
                attestedCredentialData
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(CborDecodingPerformedOnResponseObjectEvent event) {
        if (id.equals(event.getRegistrationCeremonyId())) {
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
        byte[] rpIdHash = Arrays.copyOfRange(attestationResponse.getAuthData(), 0, 32);
        boolean verified;
        byte[] rpIdBytes = RP_ID.getBytes();
        try {
            verified = SHA256.verify(rpIdHash, rpIdBytes);
            RpIdHashInAuthDataVerifiedEvent event = new RpIdHashInAuthDataVerifiedEvent(
                    command.getRegistrationCeremonyId(),
                    verified
            );
            AggregateLifecycle.apply(event);
            this.on(event);
        } catch (NoSuchAlgorithmException e) {
            throw new Fido2Exception(e);
        }
    }

    public void on(RpIdHashInAuthDataVerifiedEvent event) {
        if (!event.isVerified()) {
            throw new VerificationFailedException();
        }
    }

    /**
     * Verify if user is present in received authData from flags exactly if the first bit is set
     * step 14 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     * more on <a href="https://www.w3.org/TR/webauthn/#authenticator-data">...</a>
     *
     * @param cmd command for verifying if user is present in auth data flags
     */
    @CommandHandler
    public void handle(VerifyThatUserPresentInAuthDataIsSetCommand cmd) {
        byte userPresent = getFlags();
        boolean verified = (byte) (userPresent & 1) == 1;
        UserPresentInAuthDataVerifiedEvent event = new UserPresentInAuthDataVerifiedEvent(
                cmd.getRegistrationCeremonyId(),
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
     * step 15 from <a href="https://www.w3.org/TR/webauthn/#sctn-verifying-assertion">...</a>
     * more on <a href="https://www.w3.org/TR/webauthn/#authenticator-data">...</a>
     *
     * @param cmd command for verifying if user is present in auth data flags
     */
    @CommandHandler
    public void handle(VerifyThatUserVerifiedInAuthDataIsSetCommand cmd) {
        byte userVerified = getFlags();
        boolean verified = (byte) ((userVerified >> 2) & 1) == 1;
        UserVerifiedInAuthDataIsSetEvent event = new UserVerifiedInAuthDataIsSetEvent(
                cmd.getRegistrationCeremonyId(),
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
                cmd.getRegistrationCeremonyId(), verified
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
                    cmd.getRegistrationCeremonyId(),
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

    /**
     * Getting flags from authenticator data
     * |rpIdHash|flags|signCount|attestedCredentialData|extensions
     * |32 bytes|1 byte|4 bytes|variable optional|variable optional
     *
     * @return the bit in position 32 which is flags
     */
    private byte getFlags() {
        AuthenticatorAttestationResponse attestationResponse = repository.getAuthenticatorAttestationResponse(id);
        return attestationResponse.getAuthData()[32];
    }
}
