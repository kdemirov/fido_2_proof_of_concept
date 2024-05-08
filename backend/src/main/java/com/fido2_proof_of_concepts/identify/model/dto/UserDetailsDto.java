package com.fido2_proof_of_concepts.identify.model.dto;


import com.fido2_proof_of_concepts.identify.model.enumerations.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDetails data transfer object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String username;
    private String name;
    private Role role;
    private String userID;
    private String type;
}
