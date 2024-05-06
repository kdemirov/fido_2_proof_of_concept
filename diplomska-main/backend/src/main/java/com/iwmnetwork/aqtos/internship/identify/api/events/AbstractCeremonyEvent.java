package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Abstract authentication ceremony event.
 */
@AllArgsConstructor
@Getter
public abstract class AbstractCeremonyEvent extends AbstractEvent {
    protected Identifier ceremonyId;
}
