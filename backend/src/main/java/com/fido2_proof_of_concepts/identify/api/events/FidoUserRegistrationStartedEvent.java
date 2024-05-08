package com.fido2_proof_of_concepts.identify.api.events;

import com.fido2_proof_of_concepts.common.events.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Fido user registration started event
 */
@Getter
@AllArgsConstructor
public class FidoUserRegistrationStartedEvent extends AbstractEvent {
    private final String username;
}
