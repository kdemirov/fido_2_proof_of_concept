package com.iwmnetwork.aqtos.internship.discussion.model;

import com.iwmnetwork.aqtos.internship.discussion.api.events.CommentEditedEvent;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.EntityId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Comment Entity.
 */
@NoArgsConstructor
@Data
@Entity
public class Comment {

    @EmbeddedId
    @EntityId
    private CommentId commentId;

    @AttributeOverride(name = "id",
            column = @Column(name = "author_id", nullable = false))
    private PersonId author;

    @NotEmpty
    private String body;

    private LocalDateTime submissionDate;

    @AttributeOverride(name = "id",
            column = @Column(name = "discussion_id", nullable = false))
    private DiscussionId discussionId;

    /**
     * Constructor.
     *
     * @param commentId      commentId
     * @param author         author of the comment
     * @param body           body
     * @param submissionDate submission date
     * @param discussionId   discussion id
     */
    public Comment(CommentId commentId,
                   PersonId author,
                   String body,
                   LocalDateTime submissionDate,
                   DiscussionId discussionId) {
        this.commentId = commentId;
        this.author = author;
        this.body = body;
        this.submissionDate = submissionDate;
        this.discussionId = discussionId;
    }

    /**
     * Updates comment's content.
     *
     * @param event comment for updating the content or body of the comment
     */
    @EventHandler
    public void on(CommentEditedEvent event) {
        if (this.commentId.equals(event.getCommentId())) {
            this.body = event.getNewBody();
        }
    }
}
