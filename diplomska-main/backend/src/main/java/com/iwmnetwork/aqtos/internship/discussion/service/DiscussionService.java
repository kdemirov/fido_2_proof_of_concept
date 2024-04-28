package com.iwmnetwork.aqtos.internship.discussion.service;

import com.iwmnetwork.aqtos.internship.discussion.model.aggregates.Discussion;
import com.iwmnetwork.aqtos.internship.discussion.model.dto.DiscussionViewDto;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DiscussionService {

   Optional<DiscussionViewDto> findById(DiscussionId discussionId);

    List<Discussion> findByName(String name);

    Page<Discussion> findAllWithPagination(Pageable pageable);

    Long getNumberOfDiscussion();

}
