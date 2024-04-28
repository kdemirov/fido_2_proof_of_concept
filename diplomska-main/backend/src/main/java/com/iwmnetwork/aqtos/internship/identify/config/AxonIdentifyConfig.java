package com.iwmnetwork.aqtos.internship.identify.config;

import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.modelling.command.GenericJpaRepository;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class AxonIdentifyConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Qualifier("eventBus")
    @Autowired
    private EventBus eventBus;


    @Bean("axonUserRepository")
    public Repository<User> axonUserRepository(@Qualifier("eventBus") EventBus eventBus) {
        return GenericJpaRepository.builder(User.class)
                .entityManagerProvider(new SimpleEntityManagerProvider(entityManager))
                .eventBus(eventBus)
                .identifierConverter(UserId::new)
                .build();
    }

}
