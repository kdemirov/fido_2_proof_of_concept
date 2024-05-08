package com.iwmnetwork.aqtos.internship.discussion.model.views;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import lombok.Data;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Discussion View
 */
@Entity
@Data
@Subselect("select * from public.discussion_view")
public class DiscussionView {

    @EmbeddedId
    private DiscussionId id;

    private String name;

    private String description;

    @Column(name = "creator_name")
    private String creatorName;
}
