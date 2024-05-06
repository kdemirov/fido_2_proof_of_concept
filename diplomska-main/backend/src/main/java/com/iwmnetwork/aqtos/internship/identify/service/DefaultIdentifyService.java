package com.iwmnetwork.aqtos.internship.identify.service;

import com.iwmnetwork.aqtos.internship.identify.api.commands.AbstractCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Default identify service for dispatching commands.
 */
@RequiredArgsConstructor
@Service
public class DefaultIdentifyService {

    private final CommandGateway commandGateway;

    /**
     * Dispatches command to appropriate aggregate.
     *
     * @param cmd {@link AbstractCommand}
     * @return Completable Future of String
     */
    public CompletableFuture<String> dispatch(AbstractCommand cmd) {
        return this.commandGateway.send(cmd);
    }
}
