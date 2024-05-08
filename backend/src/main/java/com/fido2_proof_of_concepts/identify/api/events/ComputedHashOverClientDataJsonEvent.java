package com.fido2_proof_of_concepts.identify.api.events;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import lombok.Getter;

/**
 * Computed hash over client data json event.
 */
@Getter
public class ComputedHashOverClientDataJsonEvent extends AbstractCeremonyEvent {

    private final byte[] clientDataHash;

    /**
     * Constructor.
     *
     * @param id             ceremony id for ceremony aggregate
     * @param clientDataHash computed client data hash
     */
    public ComputedHashOverClientDataJsonEvent(Identifier id, byte[] clientDataHash) {
        super(id);
        this.clientDataHash = clientDataHash;
    }
}
