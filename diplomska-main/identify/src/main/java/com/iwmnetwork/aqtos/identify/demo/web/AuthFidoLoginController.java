package com.iwmnetwork.aqtos.identify.demo.web;

import com.iwmnetwork.aqtos.identify.demo.api.commands.FidoUserAuthenticateCommand;
import com.iwmnetwork.aqtos.identify.demo.model.exceptions.Fido2Exception;
import com.iwmnetwork.aqtos.identify.demo.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import com.iwmnetwork.aqtos.identify.demo.service.DefaultIdentifyService;
import com.iwmnetwork.aqtos.identify.demo.service.UserService;
import com.iwmnetwork.aqtos.identify.demo.service.impl.RelyingPartyService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/identity")
public class AuthFidoLoginController {

    private final RelyingPartyService relyingPartyService;
    private final DefaultIdentifyService defaultIdentifyService;
    private final UserService userService;

    public AuthFidoLoginController(RelyingPartyService relyingPartyService,
                                   DefaultIdentifyService defaultIdentifyService,
                                   UserService userService) {
        this.relyingPartyService = relyingPartyService;
        this.defaultIdentifyService = defaultIdentifyService;
        this.userService = userService;
    }

    @PostMapping("/login_start")
    public ResponseEntity<PublicKeyCredentialRequestOptions> startAuthentication() {
        String challenge = this.relyingPartyService.createChallenge();
        PublicKeyCredentialRequestOptions options = new PublicKeyCredentialRequestOptions(
                challenge,
                60000
        );
        return ResponseEntity.ok(options);


    }

    @PostMapping("/login_finish")
    public void validateAuthentication(@RequestBody FidoUserAuthenticateCommand command)
            throws JSONException, Fido2Exception {
    }
}
