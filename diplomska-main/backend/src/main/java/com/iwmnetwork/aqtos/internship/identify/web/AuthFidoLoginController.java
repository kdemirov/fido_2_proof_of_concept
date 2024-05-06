package com.iwmnetwork.aqtos.internship.identify.web;

import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialRequestResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import com.iwmnetwork.aqtos.internship.identify.service.AuthenticationCeremonyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for fido authentication.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/identity")
@RequiredArgsConstructor
public class AuthFidoLoginController {

    private final AuthenticationCeremonyService authenticationCeremonyService;

    @PostMapping("/login_start")
    public ResponseEntity<PublicKeyCredentialRequestOptions> startAuthentication() {
        PublicKeyCredentialRequestOptions options = authenticationCeremonyService.createRequestOptions();
        return ResponseEntity.ok(options);
    }

    @PostMapping("/login_finish")
    public void validateAuthentication(@RequestBody PublicKeyCredentialRequestResponse response) {
    }
}
