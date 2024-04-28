package com.iwmnetwork.aqtos.internship.identify.model.aggregate;


import com.iwmnetwork.aqtos.internship.identify.api.commands.FidoUserRegistrationFinishCommand;
import com.iwmnetwork.aqtos.internship.identify.api.commands.RegisterCommand;
import com.iwmnetwork.aqtos.internship.identify.api.events.FidoUserRegisteredEvent;
import com.iwmnetwork.aqtos.internship.identify.api.events.UserRegisteredEvent;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.enumerations.Role;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Aggregate(repository = "axonUserRepository")
@Table(name = "discussion_users")
@Getter
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

    @Autowired
    @Transient
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);


    public User(String username, String password, String name, String surname, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;


    }

    @CommandHandler
    public User(RegisterCommand cmd) {
        String encryptedPassword = this.passwordEncoder.encode(cmd.getPassword());
        UserRegisteredEvent event = new UserRegisteredEvent(
                new UserId(),
                cmd.getName(),
                cmd.getSurname(),
                cmd.getUsername(),
                encryptedPassword
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

    @CommandHandler
    public void handle(FidoUserRegistrationFinishCommand cmd) {
        FidoUserRegisteredEvent event = new FidoUserRegisteredEvent(
                cmd.getUserId(),
                new FidoUserId(),
                cmd.getUsername(),
                cmd.getPublicKey(),
                cmd.getCredentialId()
        );
        this.on(event);
    }

    public void on(FidoUserRegisteredEvent event) {
        this.fidoUser = new FidoUser(
                event.getFidoUserId(),
                event.getCredentialId(),
                event.getPublicKey()
        );
    }

    public User() {
    }
}
