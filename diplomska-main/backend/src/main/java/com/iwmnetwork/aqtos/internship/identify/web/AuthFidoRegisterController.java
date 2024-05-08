package com.iwmnetwork.aqtos.internship.identify.web;

import com.iwmnetwork.aqtos.internship.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
import com.iwmnetwork.aqtos.internship.identify.service.impl.RegistrationCeremonyImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * RestController for Fido registration credentials.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/identity")
@RequiredArgsConstructor
public class AuthFidoRegisterController {

    private final RegistrationCeremonyImpl registrationCeremony;

    /**
     * Fido registration start entry point.
     *
     * @param username username of the user who is trying to register a fido authenticator
     * @return created {@link PublicKeyCredentialCreationResponse}
     */
    @PostMapping("/registration_start")
    public ResponseEntity<PublicKeyCredentialCreationOptions> registrationStart(@RequestParam String username) {
        return ResponseEntity.ok(registrationCeremony.createOptions(username));
    }

    /**
     * Entry point for validation the fido authenticator.
     *
     * @param response received response from authenticator {@link PublicKeyCredentialCreationResponse}
     */
    @PostMapping("/registration_finish")
    public void registration_finish(@RequestBody PublicKeyCredentialCreationResponse response) {
    }
}
