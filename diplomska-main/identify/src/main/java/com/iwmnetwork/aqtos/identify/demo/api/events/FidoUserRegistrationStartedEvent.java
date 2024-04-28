package com.iwmnetwork.aqtos.identify.demo.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FidoUserRegistrationStartedEvent extends AbstractEvent {
    private String username;

}
