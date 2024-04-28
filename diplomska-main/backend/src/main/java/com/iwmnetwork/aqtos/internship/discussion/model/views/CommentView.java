package com.iwmnetwork.aqtos.internship.discussion.model.views;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Data;
import org.hibernate.annotations.Subselect;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

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
