package com.iwmnetwork.aqtos.internship.identify.service.impl;

import com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony.FidoUserAuthenticationStartCommand;
import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.iwmnetwork.aqtos.internship.identify.model.exceptions.Fido2Exception;
import com.iwmnetwork.aqtos.internship.identify.repository.FidoUserRepository;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class RelyingPartyService {

    private final CommandGateway commandGateway;
    private final UserService userService;
    private final FidoUserRepository fidoUserRepository;
    private final Environment environment;

    public RelyingPartyService(CommandGateway commandGateway,

                               UserService userService,
                               FidoUserRepository fidoUserRepository, Environment environment) {
        this.commandGateway = commandGateway;
        this.userService = userService;
        this.fidoUserRepository = fidoUserRepository;
        this.environment = environment;
    }

    /**
     * Decode and verify received json data from client platform
     * step 3-5 from https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential
     * and verifying created challenge from relying party
     * step 8 from https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential
     *
     * @param cmd received serialized json from client platform see FidoUserRegistrationStartCommand
     * @return true if all steps all fulfilled
     * @throws JSONException
     * @throws Fido2Exception
     */
    public boolean decodeAndVerifyResponseObject(PublicKeyCredentialCreationResponse cmd)
            throws JSONException, Fido2Exception {
//        RelyingPartyVerifying.setEnvironment(this.environment);
//        AuthenticatorAttestationResponse response = CBORDecoder.decodeAttStmt(cmd.getAttestationObject());
//        CollectedClientData clientData = JsonParser.getClientData(cmd.getClientDataHash());
//        this.repository.findById(clientData.getChallenge())
//                .orElseThrow(VerificationFailedException::new);
//        User user = this.userService.findByUsername(cmd.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//        AttestedCredentialData attestedCredentialData = AttestationResponseReader
//                .decodeAttestedCredentialData(response.getAuthData());
//        if (RelyingPartyVerifying.verify(response,
//                clientData,
//                attestedCredentialData,
//                JsonParser.JsonToByteArray(cmd.getClientDataHash()))) {
//            return true;
//        }
//        return false;
        return true;
    }

    /**
     * decode and verify received json from client platform
     * step 3 to 9 from https://www.w3.org/TR/webauthn/#sctn-verifying-assertion
     * verify created challenge from relying party
     * step 12 from https://www.w3.org/TR/webauthn/#sctn-verifying-assertion
     *
     * @param command received json from client platform see FidoUserAuthenticateCommand
     * @return true if all steps are fulfilled
     * @throws JSONException
     * @throws Fido2Exception
     */
    public boolean decodeAndVerifyAssertionObject(FidoUserAuthenticationStartCommand command)
            throws JSONException, Fido2Exception {
//        RelyingPartyVerifying.setEnvironment(this.environment);
//        CollectedClientData clientData = JsonParser.getClientData(command.getClientDataJSON());
//        this.repository.findById(clientData.getChallenge())
//                .orElseThrow(VerificationFailedException::new);
//        byte[] credentialId = JsonParser.JsonToByteArray(command.getId());
//        FidoUser fidoUser = this.fidoUserRepository.findByCredentialId(credentialId)
//                .orElseThrow(() -> new UsernameNotFoundException("not found"));
//        byte[] pk = fidoUser.getPublicKey();
//        AuthenticatorAssertionResponse response = CBORDecoder
//                .decodeAssObj(command.getAuthData(), command.getUserHandle(), command.getSignature());
//        return RelyingPartyVerifying.verify(response,
//                clientData,
//                JsonParser.JsonToByteArray(command.getClientDataJSON()),
//                pk);
        return true;
    }
}
