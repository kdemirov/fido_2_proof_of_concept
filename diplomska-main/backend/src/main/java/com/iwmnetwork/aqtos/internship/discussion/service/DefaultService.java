package com.iwmnetwork.aqtos.internship.discussion.service;

import com.iwmnetwork.aqtos.internship.discussion.api.commands.AbstractCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DefaultService {

    private final CommandGateway commandGateway;

    public DefaultService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> dispatch(AbstractCommand command) {
            return this.commandGateway.send(command);
    }
}
