package com.iwmnetwork.aqtos.internship.identify.model.aggregate;


import com.iwmnetwork.aqtos.internship.identify.api.commands.FidoUserRegistrationFinishCommand;
import com.iwmnetwork.aqtos.internship.identify.api.commands.RegisterCommand;
import com.iwmnetwork.aqtos.internship.identify.api.commands.UpdateFidoUserSignCountCommand;
import com.iwmnetwork.aqtos.internship.identify.api.events.FidoUserRegisteredEvent;
import com.iwmnetwork.aqtos.internship.identify.api.events.FidoUserSignCountUpdatedEvent;
import com.iwmnetwork.aqtos.internship.identify.api.events.UserRegisteredEvent;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.enumerations.Role;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.*;

/**
 * User Aggregate.
 */
@Entity
@Aggregate(repository = "axonUserRepository")
@Table(name = "discussion_users")
@Getter
@NoArgsConstructor
public class User {
    @EmbeddedId
    @AggregateIdentifier
    private UserId id;
    private String name;
    private String surname;
    private String username;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private FidoUser fidoUser;
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Constructor.
     *
     * @param username username
     * @param password encoded password
     * @param name     name
     * @param surname  surname
     * @param role     role
     */
    public User(String username, String password, String name, String surname, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;


    }

    /**
     * Register user command.
     *
     * @param cmd {@link RegisterCommand}
     */
    @CommandHandler
    public User(RegisterCommand cmd) {
        UserRegisteredEvent event = new UserRegisteredEvent(
                new UserId(),
                cmd.getName(),
                cmd.getSurname(),
                cmd.getUsername(),
                cmd.getPassword()
        );
        this.on(event);
    }

    public void on(UserRegisteredEvent event) {
        this.id = event.getUserId();
        this.name = event.getName();
        this.surname = event.getSurname();
        this.username = event.getUsername();
        this.password = event.getEncryptedPassword();
        this.role = Role.ROLE_USER;
    }

    /**
     * Saves instance of fido user if all defined steps are verified.
     *
     * @param cmd {@link FidoUserRegistrationFinishCommand}
     */
    @CommandHandler
    public void handle(FidoUserRegistrationFinishCommand cmd) {
        FidoUserRegisteredEvent event = new FidoUserRegisteredEvent(
                cmd.getUserId(),
                new FidoUserId(),
                cmd.getUsername(),
                cmd.getPublicKey(),
                cmd.getCredentialId(),
                cmd.getSignCount()
        );
        AggregateLifecycle.apply(event);
        this.on(event);
    }

    public void on(FidoUserRegisteredEvent event) {
        this.fidoUser = new FidoUser(
                event.getFidoUserId(),
                event.getCredentialId(),
                event.getPublicKey(),
                event.getSignCount()
        );
    }

    /**
     * Update signature counter value of registered fido user.
     *
     * @param cmd {@link UpdateFidoUserSignCountCommand}
     */
    @CommandHandler
    public void handle(UpdateFidoUserSignCountCommand cmd) {
        FidoUserSignCountUpdatedEvent event = new FidoUserSignCountUpdatedEvent(
                cmd.getFidoUserId(),
                cmd.getStoredCount()
        );
        AggregateLifecycle.apply(event);
    }
}
