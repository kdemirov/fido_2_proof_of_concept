package com.fido2_proof_of_concepts.identify.api.events;


import com.fido2_proof_of_concepts.common.events.AbstractEvent;
import com.fido2_proof_of_concepts.identify.model.identifiers.FidoUserId;
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
