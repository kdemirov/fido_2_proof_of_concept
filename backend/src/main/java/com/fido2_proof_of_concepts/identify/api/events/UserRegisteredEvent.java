package com.fido2_proof_of_concepts.identify.api.events;

import com.fido2_proof_of_concepts.identify.model.identifiers.UserId;
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
