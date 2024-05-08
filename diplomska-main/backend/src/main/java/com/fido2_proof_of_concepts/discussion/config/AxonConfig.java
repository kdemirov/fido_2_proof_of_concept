package com.iwmnetwork.aqtos.internship.discussion.config;

import com.iwmnetwork.aqtos.internship.discussion.model.Comment;
import com.iwmnetwork.aqtos.internship.discussion.model.aggregates.Discussion;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.modelling.command.GenericJpaRepository;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Axon config configuration.
 */
@Configuration
public class AxonConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean("axonCommentRepository")
    public Repository<Comment> commentGenericJpaRepository(@Qualifier("eventBus") EventBus eventBus) {
        return GenericJpaRepository.builder(Comment.class)
                .entityManagerProvider(new SimpleEntityManagerProvider(entityManager))
                .eventBus(eventBus)
                .identifierConverter(CommentId::new)
                .build();
    }

    @Bean("axonDiscussionRepository")
    public Repository<Discussion> axonDiscussionRepository(@Qualifier("eventBus") EventBus eventBus) {
        return GenericJpaRepository.builder(Discussion.class)
                .entityManagerProvider(new SimpleEntityManagerProvider(entityManager))
                .eventBus(eventBus)
                .identifierConverter(DiscussionId::new)
                .build();
    }

    @Bean
    @Qualifier("eventBus")
    public EventBus eventBus() {
        return new SimpleEventBus.Builder()
                .build();
    }

    @Autowired
    public void configureBeanValidation(@Qualifier("eventBus") EventBus eventBus) {
        eventBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
    }
}
