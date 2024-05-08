package com.iwmnetwork.aqtos.internship.discussion.service;

import com.iwmnetwork.aqtos.internship.discussion.model.aggregates.Discussion;
import com.iwmnetwork.aqtos.internship.discussion.model.dto.DiscussionViewDto;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service for a {@link Discussion}.
 */
public interface DiscussionService {

    /**
     * Returns discussion view by given discussion id.
     *
     * @param discussionId given discussion id
     * @return optional of {@link DiscussionViewDto}
     */
    Optional<DiscussionViewDto> findById(DiscussionId discussionId);

    /**
     * Finds discussions that contains the given string in their name.
     *
     * @param name given string
     * @return list of {@link Discussion}
     */
    List<Discussion> findByName(String name);

    /**
     * Finds all discussions with page request.
     *
     * @param pageable page request
     * @return page of {@link Discussion}
     */
    Page<Discussion> findAllWithPagination(Pageable pageable);

    /**
     * Count the number of created discussions.
     *
     * @return number of created discussions
     */
    Long getNumberOfDiscussion();
}
