package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
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
