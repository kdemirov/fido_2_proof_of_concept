package com.iwmnetwork.aqtos.internship.identify.model.dto;

import com.iwmnetwork.aqtos.internship.identify.model.enumerations.Role;
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
