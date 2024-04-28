package com.iwmnetwork.aqtos.identify.demo.service;

import com.iwmnetwork.aqtos.identify.demo.api.commands.AbstractCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DefaultIdentifyService {

    private final CommandGateway commandGateway;

    public DefaultIdentifyService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> dispatch(AbstractCommand cmd) {
        return this.commandGateway.send(cmd);
    }
}
