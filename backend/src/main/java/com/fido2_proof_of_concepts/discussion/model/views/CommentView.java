package com.fido2_proof_of_concepts.discussion.model.views;

import com.fido2_proof_of_concepts.discussion.model.identifiers.CommentId;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import lombok.Data;
import org.hibernate.annotations.Subselect;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Comment view.
 */
@Data
@Subselect("select * from public.comment_view")
@Entity
public class CommentView {

    @EmbeddedId
    private CommentId id;

    private String body;

    @Column(name = "name")
    private String authorName;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @AttributeOverride(name = "id",
            column = @Column(name = "discussion_id", nullable = false))
    private DiscussionId discussionId;
}
