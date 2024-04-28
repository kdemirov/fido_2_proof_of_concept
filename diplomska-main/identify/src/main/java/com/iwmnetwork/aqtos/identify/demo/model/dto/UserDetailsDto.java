package com.iwmnetwork.aqtos.identify.demo.model.dto;

import com.iwmnetwork.aqtos.identify.demo.model.enumerations.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String username;
    private String name;
    private Role role;
    private String userID;

}
