package com.iwmnetwork.aqtos.internship.discussion.repository;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.views.DiscussionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa repository for {@link DiscussionView}.
 */
@Repository
public interface DiscussionViewRepository extends JpaRepository<DiscussionView, DiscussionId> {
}
