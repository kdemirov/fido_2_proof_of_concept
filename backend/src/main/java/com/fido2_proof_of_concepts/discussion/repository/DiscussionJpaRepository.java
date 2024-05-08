package com.fido2_proof_of_concepts.discussion.repository;


import com.fido2_proof_of_concepts.discussion.model.aggregates.Discussion;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Jpa repository for {@link Discussion}.
 */
@Repository
public interface DiscussionJpaRepository extends JpaRepository<Discussion, DiscussionId> {

    /**
     * Finds discussions that contains the given string in the discussion's name.
     *
     * @param name given string
     * @return list of {@link Discussion}
     */
    List<Discussion> findByNameLike(String name);


    /**
     * Returns discussions in a page.
     *
     * @param pageable pageable request
     * @return page of {@link Discussion}
     */
    @Override
    Page<Discussion> findAll(Pageable pageable);
}
