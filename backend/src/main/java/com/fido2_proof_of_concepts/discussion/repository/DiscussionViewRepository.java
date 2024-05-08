package com.fido2_proof_of_concepts.discussion.repository;

import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import com.fido2_proof_of_concepts.discussion.model.views.DiscussionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa repository for {@link DiscussionView}.
 */
@Repository
public interface DiscussionViewRepository extends JpaRepository<DiscussionView, DiscussionId> {
}
