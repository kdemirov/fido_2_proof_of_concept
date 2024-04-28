package com.iwmnetwork.aqtos.internship.identify.web;

import com.iwmnetwork.aqtos.identify.demo.api.commands.FidoUserRegistrationStartCommand;
import com.iwmnetwork.aqtos.identify.demo.bootstrap.Constants;
import com.iwmnetwork.aqtos.identify.demo.model.exceptions.Fido2Exception;
import com.iwmnetwork.aqtos.identify.demo.repository.webauthn.authenticator_model.*;
import com.iwmnetwork.aqtos.identify.demo.repository.webauthn.enumerations.AttestationConveyancePreference;
import com.iwmnetwork.aqtos.identify.demo.repository.webauthn.enumerations.UserVerificationRequirements;
import com.iwmnetwork.aqtos.identify.demo.service.DefaultIdentifyService;
import com.iwmnetwork.aqtos.identify.demo.service.impl.RelyingPartyService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/identity")
public class AuthFidoRegisterController {

    private final DefaultIdentifyService defaultIdentifyService;
    private final RelyingPartyService service;


    public AuthFidoRegisterController(DefaultIdentifyService defaultIdentifyService, RelyingPartyService service) {
        this.defaultIdentifyService = defaultIdentifyService;
        this.service = service;
    }

    @PostMapping("/registration_start")
    public ResponseEntity<PublicKeyCredentialCreationOptions> registrationStart(@RequestParam String username) {
        String challenge = this.service.createChallenge();
        PublicKeyCredentialCreationOptions options = new PublicKeyCredentialCreationOptions(
                new PublicKeyCredentialRpEntity(Constants.rpId),
                new PublicKeyCredentialUserEntity(UUID.randomUUID().toString(), username),
                challenge,
                new PublicKeyCredentialParameters(),
                60000,
                new AuthenticatorSelectionCriteria(UserVerificationRequirements.DISCOURAGED.requirement),
                AttestationConveyancePreference.DIRECT.attestationConveyancePreference

        );
        return ResponseEntity.ok(options);
    }

    @PostMapping("/registration_finish")
    public ResponseEntity registrationFinish(@RequestBody FidoUserRegistrationStartCommand cmd)
            throws JSONException, Fido2Exception {
        if (this.service.decodeAndVerifyResponseObject(cmd)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
