package com.fido2_proof_of_concepts.discussion.model.aggregates;

import com.fido2_proof_of_concepts.discussion.api.commands.*;
import com.fido2_proof_of_concepts.discussion.api.events.*;
import com.fido2_proof_of_concepts.discussion.model.Comment;
import com.fido2_proof_of_concepts.discussion.model.identifiers.CommentId;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import com.fido2_proof_of_concepts.discussion.model.identifiers.PersonId;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

/**
 * Discussion aggregate.
 */
@NoArgsConstructor
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

    /**
     * Creates discussion with attributes from command.
     *
     * @param cmd {@link CreateDiscussionCommand}
     */
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

    /**
     * Creates discussion with participants.
     *
     * @param cmd {@link CreateDiscussionWithMembersCommand}
     */
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
        this.description = event.getDescription();
        this.creator = event.getCreator();
        this.comments = new TreeSet<>(Comparator.comparing(Comment::getSubmissionDate));
        this.members = event.getMembers();
    }

    /**
     * Adds comment to a discussion.
     *
     * @param cmd {@link AddCommentCommand }
     * @return added {@link CommentId}
     */
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

    /**
     * Deletes comment from a discussion.
     *
     * @param cmd {@link RemoveCommentCommand}
     */
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

    /**
     * Updates comment's content
     *
     * @param cmd {@link UpdateCommentCommand}
     */
    @CommandHandler
    public void handle(UpdateCommentCommand cmd) {
        CommentEditedEvent event = new CommentEditedEvent(
                cmd.getDiscussionId(),
                cmd.getCommentId(),
                cmd.getNewBody()

        );
        AggregateLifecycle.apply(event);
    }

    /**
     * Adds member to a discussion.
     *
     * @param cmd {@link AddMemberCommand}
     */
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

    /**
     * Removes member from a discussion.
     *
     * @param cmd {@link RemoveMemberCommand}
     */
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
}
