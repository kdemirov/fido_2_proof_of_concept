package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Fido user signature counter updated event.
 */
@AllArgsConstructor
@Getter
public class FidoUserSignCountUpdatedEvent extends AbstractEvent {
    private final FidoUserId fidoUserId;
    private final int signCount;
}
