package com.fido2_proof_of_concepts.identify.api.events;


import com.fido2_proof_of_concepts.common.events.AbstractEvent;
import com.fido2_proof_of_concepts.common.identifiers.Identifier;
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
