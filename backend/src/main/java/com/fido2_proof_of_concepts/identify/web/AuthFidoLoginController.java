package com.fido2_proof_of_concepts.identify.web;

import com.fido2_proof_of_concepts.identify.model.dto.PublicKeyCredentialRequestResponse;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import com.fido2_proof_of_concepts.identify.service.AuthenticationCeremonyService;
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

    /**
     * Start fido authentication entry point.
     *
     * @return created {@link PublicKeyCredentialRequestOptions}
     */
    @PostMapping("/login_start")
    public ResponseEntity<PublicKeyCredentialRequestOptions> startAuthentication() {
        PublicKeyCredentialRequestOptions options = authenticationCeremonyService.createRequestOptions();
        return ResponseEntity.ok(options);
    }

    /**
     * Entry point for validation the fido authenticator.
     *
     * @param response received response from authenticator {@link PublicKeyCredentialRequestResponse}
     */
    @PostMapping("/login_finish")
    public void validateAuthentication(@RequestBody PublicKeyCredentialRequestResponse response) {
    }
}
