package com.iwmnetwork.aqtos.internship.discussion.service;

import com.iwmnetwork.aqtos.internship.discussion.api.commands.AbstractCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Default service for dispatching a command to an aggregate.
 */
@RequiredArgsConstructor
@Service
public class DefaultService {

    private final CommandGateway commandGateway;

    /**
     * Dispatches command to an aggregate
     *
     * @param command {@link AbstractCommand}
     * @return completable future of string
     */
    public CompletableFuture<String> dispatch(AbstractCommand command) {
        return this.commandGateway.send(command);
    }
}
