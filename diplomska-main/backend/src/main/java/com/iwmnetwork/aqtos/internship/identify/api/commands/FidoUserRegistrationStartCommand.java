package com.iwmnetwork.aqtos.internship.identify.api.commands;

import lombok.Data;


@Data
public class FidoUserRegistrationStartCommand extends AbstractCommand {

    private String id;
    private String type;
    private String attestationObject;
    private String username;
    private String clientDataHash;


    public FidoUserRegistrationStartCommand(String id,
                                            String type,
                                            String response,
                                            String username,
                                            String clientDataHash) {
        this.id = id;
        this.type = type;
        this.attestationObject = response;
        this.username = username;
        this.clientDataHash = clientDataHash;
    }

    public FidoUserRegistrationStartCommand() {
    }
}
