package com.iwmnetwork.aqtos.identify.demo.api.events;

import com.iwmnetwork.aqtos.identify.demo.model.identifiers.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegisteredEvent {
    private UserId userId;
    private String name;
    private String surname;
    private String username;
    private String encryptedPassword;
}
