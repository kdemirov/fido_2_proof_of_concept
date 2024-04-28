package com.iwmnetwork.aqtos.identify.demo.api.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FidoUserAuthenticateCommand {
    private String id;
    private String signature;
    private String authData;
    private String userHandle;
    private String clientDataJSON;
    private String type;

}
