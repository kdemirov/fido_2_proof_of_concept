package com.iwmnetwork.aqtos.internship.identify.model;

import com.iwmnetwork.aqtos.internship.identify.api.events.FidoUserSignCountUpdatedEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.eventhandling.EventHandler;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Fido user entity.
 */
@NoArgsConstructor
@Entity
@Getter
@AllArgsConstructor
public class FidoUser {
    @EmbeddedId
    private FidoUserId fidoUserId;
    private byte[] credentialId;
    private byte[] publicKey;
    private int signCount;

    /**
     * Updates fido user's signature counter.
     *
     * @param event {@link FidoUserSignCountUpdatedEvent}
     */
    @EventHandler
    public void handle(FidoUserSignCountUpdatedEvent event) {
        if (this.fidoUserId.equals(event.getFidoUserId())) {
            this.signCount = event.getSignCount();
        }
    }
}
