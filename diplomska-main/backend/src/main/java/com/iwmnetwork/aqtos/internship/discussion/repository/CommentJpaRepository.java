package com.iwmnetwork.aqtos.internship.discussion.repository;

import com.iwmnetwork.aqtos.internship.discussion.model.Comment;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa repository for {@link Comment}.
 */
@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {
}
