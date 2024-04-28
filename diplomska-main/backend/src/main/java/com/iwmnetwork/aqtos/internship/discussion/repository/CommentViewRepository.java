package com.iwmnetwork.aqtos.internship.discussion.repository;

import com.iwmnetwork.aqtos.internship.discussion.model.Comment;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.views.CommentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;
@Repository
public interface CommentViewRepository extends JpaRepository<CommentView, CommentId> {

    @OrderBy("submission_date")
    List<CommentView> findAllByDiscussionId(DiscussionId discussionId);
}
