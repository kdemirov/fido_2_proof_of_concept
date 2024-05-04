package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Abstract Registration Ceremony event.
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AbstractRegistrationCeremonyEvent extends AbstractEvent {
    protected RegistrationCeremonyId registrationCeremonyId;
}
