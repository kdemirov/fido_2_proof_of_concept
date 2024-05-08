package com.fido2_proof_of_concepts.discussion.repository;


import com.fido2_proof_of_concepts.discussion.model.Comment;
import com.fido2_proof_of_concepts.discussion.model.identifiers.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa repository for {@link Comment}.
 */
@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {
}
