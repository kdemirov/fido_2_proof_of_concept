package com.iwmnetwork.aqtos.internship.discussion.model.aggregates;

import com.iwmnetwork.aqtos.internship.discussion.api.commands.*;
import com.iwmnetwork.aqtos.internship.discussion.api.events.*;
import com.iwmnetwork.aqtos.internship.discussion.model.*;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Data;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;

import org.axonframework.spring.stereotype.Aggregate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Aggregate(repository = "axonDiscussionRepository")
@Getter
public class Discussion {

    @AggregateIdentifier
    @EmbeddedId
    private DiscussionId id;
    @NotEmpty
    private String name;
    @AttributeOverride(name = "id",
            column = @Column(name = "creator_id", nullable = false))
    private PersonId creator;

    private String description;

    @OneToMany(cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @AggregateMember
    @OrderBy("submissionDate")
    private Set<Comment> comments;

    @ElementCollection
    private List<PersonId> members;

    // create discussion command: constructor
    @CommandHandler
    public Discussion(CreateDiscussionCommand cmd) {
        DiscussionCreatedEvent event = new DiscussionCreatedEvent(
                new DiscussionId(),
                cmd.getName(),
                cmd.getDescription(),
                cmd.getCreator(),
                new ArrayList<>());
        this.on(event);
    }

    @CommandHandler
    public Discussion(CreateDiscussionWithMembersCommand cmd) {
        DiscussionCreatedEvent event = new DiscussionCreatedEvent(
                new DiscussionId(),
                cmd.getName(),
                cmd.getDescription(),
                cmd.getCreator(),
                cmd.getMembers()
        );
        this.on(event);
    }

    public void on(DiscussionCreatedEvent event) {
        this.id = event.getDiscussionId();
        this.name = event.getName();
        this.name = event.getName();
        this.description = event.getDescription();
        this.creator = event.getCreator();
        this.comments = new TreeSet<>(Comparator.comparing(Comment::getSubmissionDate));
        this.members = event.getMembers();
    }

    // add comment
    @CommandHandler
    public CommentId handle(AddCommentCommand cmd) {
        CommentId commentId = new CommentId();
        CommentAddedEvent event = new CommentAddedEvent(
                cmd.getDiscussionId(),
                commentId,
                cmd.getAuthor(),
                cmd.getBody()
        );
        this.on(event);
        return commentId;
    }

    public void on(CommentAddedEvent event) {
        this.comments.add(new Comment(
                event.getCommentId(),
                event.getAuthor(),
                event.getBody(),
                LocalDateTime.now(),
                event.getDiscussionId()
        ));

    }

    // remove comment
    @CommandHandler
    public void handle(RemoveCommentCommand cmd) {
        CommentRemovedEvent event = new CommentRemovedEvent(
                cmd.getDiscussionId(),
                cmd.getCommentId()
        );
        this.on(event);
    }

    public void on(CommentRemovedEvent event) {
        Optional<Comment> optionalComment = this.comments.stream().
                filter(c -> c.getCommentId().equals(event.getCommentId()))
                .findFirst();
        optionalComment.ifPresent(value -> this.comments.remove(value));

    }

    // update comment
    @CommandHandler
    public void handle(UpdateCommentCommand cmd) {
        CommentEditedEvent event = new CommentEditedEvent(
                cmd.getDiscussionId(),
                cmd.getCommentId(),
                cmd.getNewBody()

        );
        AggregateLifecycle.apply(event);

    }

    @CommandHandler
    public void handle(AddMemberCommand cmd) {
        MemberAddedEvent event = new MemberAddedEvent(
                cmd.getDiscussionId(),
                cmd.getMember()
        );
        this.on(event);
    }

    public void on(MemberAddedEvent event) {
        this.members.add(event.getMember());

    }

    @CommandHandler
    private void handle(RemoveMemberCommand cmd) {
        MemberRemovedEvent event = new MemberRemovedEvent(
                cmd.getDiscussionId(),
                cmd.getMember()
        );
        this.on(event);
    }

    public void on(MemberRemovedEvent event) {
        this.members = this.members.stream()
                .filter(m -> !m.equals(event.getMember()))
                .collect(Collectors.toList());

    }

    //
    public Discussion() {
    }

}
