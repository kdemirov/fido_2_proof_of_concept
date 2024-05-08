package com.iwmnetwork.aqtos.internship.identify.api.events;

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
