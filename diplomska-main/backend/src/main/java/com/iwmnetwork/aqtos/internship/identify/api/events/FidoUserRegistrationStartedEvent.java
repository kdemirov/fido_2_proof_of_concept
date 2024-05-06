package com.iwmnetwork.aqtos.internship.identify.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FidoUserRegistrationStartedEvent extends AbstractEvent {
    private final String username;
}
